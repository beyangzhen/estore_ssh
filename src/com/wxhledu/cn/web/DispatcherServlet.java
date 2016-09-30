package com.wxhledu.cn.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 总路径分发器，完成路径 UserCtrl?method=login等， 调用UserCtrl类的login等方法
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 等价于 @Slf4j
	// private static final Logger log = LoggerFactory.getLogger(ClassFileVistor.class);

	/**
	 * 类名 和 类的实例化对象 的键值对
	 */
	private ConcurrentMap<String, Object> objectCache = new ConcurrentHashMap<>();

	public DispatcherServlet() {
		super();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// 获取开始路径
		String classes = config.getServletContext().getRealPath("/WEB-INF/classes/");
		
		// 文件查找（Files类）
		try {
			// 遍历该文件下所有class文件，添加到objectCache集合中
			Files.walkFileTree(Paths.get(classes), new ClassFileVistor(objectCache));
		} catch (IOException e) {
			// e.printStackTrace();
			log.error("查找类异常:{}", e.getMessage());
		}
		
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取页面请求路径的uri（/estore/UserCtrl）
		String className = request.getRequestURI().substring(request.getContextPath().length() + 1);
		
		if (StringUtils.isBlank(className)) { // commons.lang.StringUtils 
			log.error("访问的类名称不存在");
			return;
		}
		if (StringUtils.contains(className, "/")) {
			className = className.substring(className.lastIndexOf("/") + 1); // admin/UserCtrl
		}
		if (StringUtils.contains(className, ";")) {
			className = className.substring(0, className.indexOf(";")); // UserCtrl;jsession=xxxx
		}
		// 根据页面请求路径中的类名，获取出相应的UserCtrl类
		Object object = objectCache.get(className);
		
		// 获取请求的方法
		String methodName = request.getParameter("method");
		if (StringUtils.isBlank(methodName)) {
			log.error("请求的方法参数不存在");
			return;
		}
		log.info("用户访问:{}.{}", className, methodName);			

		Method method = null;
		if (null != object) {
			try {
				method = object.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			} catch (NoSuchMethodException | SecurityException e) {
				// e.printStackTrace();
				log.error("访问类实例对象{}的方法{}不存在", className, methodName);
			}
		}
		if (null == method) {
			log.error("请求的方法不存在");
			return;
		}
		
		// 执行方法（反射）
		try {
			// 方法返回的自定义url（前缀为f：转发    前缀为r：重定向）
			String view = (String) method.invoke(object, request, response);
			
			if(StringUtils.isNotBlank(view)){
				int index = view.indexOf(':'); 
				
				if (index == -1) { // 返回路径中没冒号，使用普通转发
					request.getRequestDispatcher(view).forward(request, response);
					return;
				} else { // 返回路径中有冒号
					String start = view.substring(0, index); 
					String path = view.substring(index + 1); 
					if (start.equals("f")) { 
						// 前缀为f，使用转发方式
						request.getRequestDispatcher(path).forward(request, response);
						return;
						// System.out.println("******************first*****************"); // 不执行
					} else if (start.equals("r")) { 
						// 前缀为r，使用重定向方式
						response.sendRedirect(request.getContextPath() + path);
						return;
					}
				}
				// System.out.println("******************second*****************"); // 执行（跳转回来后还会继续执行）
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			// 6. 无返回的
		}
	}

}


//方式一：配置文件 UserCtrl=com.wxhledu.cn.web.UserCtrl
/**
 * 方式二：注解方法（文件查找，扫描带@Controller 类）
 * 
 */
@Slf4j
class ClassFileVistor extends SimpleFileVisitor<Path> {
	
	// 相当于写 @Slf4j
	// private static final Logger log = LoggerFactory.getLogger(ClassFileVistor.class);

	private ConcurrentMap<String, Object> map;

	public ClassFileVistor(ConcurrentMap<String, Object> objectCache) {
		this.map = objectCache;
	}

	// 自动执行访问 Files.walkFileTree()中指定路径下的所有文件
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		// 获取指定路径下，文件全名
		String name = file.toFile().getAbsolutePath();
		
		// 筛选出.class结尾的文件
		if (name.contains(".class")) {
			String n = name.substring(name.indexOf("classes") + "classes".length() + 1, name.length() - ".class".length());
			log.info("找到{}类", n);
			n = n.replace("\\", ".");
			try {
				// 类名字符串 转成  Class
				Class<?> clz = Class.forName(n, false, this.getClass().getClassLoader());
				if (null != clz) {
					// 筛选出有Controller注解的类
					Controller controller = clz.getAnnotation(Controller.class);
					if (null != controller) {
						log.info("找到我要处理的类简称：{}，全称：{}", clz.getSimpleName(), clz.getCanonicalName());
						map.put(clz.getSimpleName(), clz.newInstance());
					}
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				log.error("加载类异常：{}", e.getMessage());
			}
		}
		
		return FileVisitResult.CONTINUE;
	}

}