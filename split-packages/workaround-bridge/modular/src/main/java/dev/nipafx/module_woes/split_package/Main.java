package dev.nipafx.module_woes.split_package;

import dev.nipafx.module_woes.split_package.bridge.Bridge;
import org.apache.lucene.document.Document;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Document doc = new Bridge().read();
	}

}
