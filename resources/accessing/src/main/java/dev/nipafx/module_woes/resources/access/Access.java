package dev.nipafx.module_woes.resources.access;

import dev.nipafx.module_woes.resources.mod.Anchor;

import java.util.Optional;
import java.util.Scanner;

public class Access {

	public static void main(String[] args) throws Exception {
		String resultFromSameJar = getResourceFromSameJar();
		System.out.println("Loaded: " + resultFromSameJar);

		String resultFromOtherJar = getResourceFromOtherJarWithAnchor();
		System.out.println("Loaded: " + resultFromOtherJar);
	}

	private static String getResourceFromSameJar() {
		return Optional
				// TODO: move resource to different package to demonstrate how to load that
				.ofNullable(Access.class.getResourceAsStream("file.txt"))
				.map(stream -> new Scanner(stream).useDelimiter("\\A"))
				.map(scanner -> scanner.hasNext() ? scanner.next() : null)
				.orElse("COULDN'T LOAD RESOURCE");
	}

	private static String getResourceFromOtherJarWithAnchor() {
		return Optional
				// TODO: move resource to different package to demonstrate how to load that
				.ofNullable(Anchor.class.getResourceAsStream("file.txt"))
				.map(stream -> new Scanner(stream).useDelimiter("\\A"))
				.map(scanner -> scanner.hasNext() ? scanner.next() : null)
				.orElse("COULDN'T LOAD RESOURCE");
	}

}
