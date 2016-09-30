package com.wxhledu.cn;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.Test;

public class PathTest {

	@Test
	public void test() throws IOException {
		Files.walkFileTree(Paths.get("C:\\Users\\qinhaizong\\WXHL\\2016_haizong\\workspace_std\\estore"), new SimpleFileVisitor<Path>() {
			 @Override
			 public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				 // Files.delete(file);
				 System.out.println(file);
				 
				 return FileVisitResult.CONTINUE;
			 }

			 /*
			 @Override
			 public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
				 if (e == null) {
					 //Files.delete(dir);
					 return FileVisitResult.CONTINUE;
				 } else {
					 // directory iteration failed
					 throw e;
				 }
			 }
			 */

		});
	}
}
