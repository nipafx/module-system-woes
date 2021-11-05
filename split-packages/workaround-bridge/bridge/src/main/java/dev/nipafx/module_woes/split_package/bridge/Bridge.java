package dev.nipafx.module_woes.split_package.bridge;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bridge {

	// use Lucene Analyzers Common types only here

	public Document read() throws IOException {
		Directory memoryIndex = new RAMDirectory();
		Analyzer analyzer = new ClassicAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		IndexWriter writter = new IndexWriter(memoryIndex, indexWriterConfig);
		Document document = new Document();

		document.add(new TextField("title", "A Title", Field.Store.YES));
		document.add(new TextField("body", "This is the body text.", Field.Store.YES));

		writter.addDocument(document);
		writter.close();

		return document;
	}

	public List<Document> searchIndex(String inField, String queryString) throws IOException {
		Directory memoryIndex = new RAMDirectory();

		Query query = new PhraseQuery("title", "Title");

		IndexReader indexReader = DirectoryReader.open(memoryIndex);
		IndexSearcher searcher = new IndexSearcher(indexReader);
		TopDocs topDocs = searcher.search(query, 10);
		List<Document> documents = new ArrayList<>();
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			documents.add(searcher.doc(scoreDoc.doc));
		}

		return documents;
	}

}
