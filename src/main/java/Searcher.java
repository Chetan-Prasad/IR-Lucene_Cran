import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Searcher {

    public void searchCran() throws Exception {
        String index = "Index";
        String field = "Words";
        String queries = "cran.qry";
        String line;
        int hitsPerPage = 10;

        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
        IndexSearcher searcher = new IndexSearcher(reader);
        searcher.setSimilarity(new ClassicSimilarity());
        Analyzer analyzer = new EnglishAnalyzer();

        BufferedReader in;
        if (queries != null) {
            in = Files.newBufferedReader(Paths.get(queries), StandardCharsets.UTF_8);
        } else {
            in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        }
        QueryParser parser = new QueryParser(field, analyzer);
        HashMap<String, Float> boost = new HashMap<>();
        boost.put("Title", 0.2f);
        boost.put("Words", 0.7f);
        boost.put("Bibliography", 0.1f);
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[] {"ID","Title", "Authors", "Bibliography", "Words"}, analyzer, boost);
        Query query;
        StringBuilder sb = new StringBuilder();
        int queryId = 1;
        List<String> queryResults = new ArrayList<>();

        while((line=in.readLine()) != null) {
            if (line.startsWith(".I")) {
                if(sb.length() != 0) {
                    query = queryParser.parse(sb.toString());
                    queryResults.addAll(doSearch(searcher, query, hitsPerPage, queryId));
                    queryId++;
                    sb.delete(0, sb.length());
                }
                if((line=in.readLine()) == null) break;
            }
            else{
                line = line.replace("?", "");
                sb.append(line).append(" ");
            }
        }
        if(sb.length() != 0) {
            query = parser.parse(sb.toString());
            queryResults.addAll(doSearch(searcher, query, hitsPerPage, queryId));
            sb.delete(0, sb.length());
        }
        reader.close();
        Files.write(Paths.get("result.txt"), queryResults, StandardCharsets.UTF_8);
    }

    public static List<String> doSearch(IndexSearcher searcher, Query query,
                                        int hitsPerPage, int queryId) throws IOException {

        // Collect enough docs to show 5 pages
        TopDocs results = searcher.search(query, 5 * hitsPerPage);
        ScoreDoc[] hits = results.scoreDocs;
        List<String> queryresult = new ArrayList<>();

        for(int i = 0; i < hits.length; i++) {
            Document doc = searcher.doc(hits[i].doc);
            String path = doc.get("ID");
            if (path != null) {
                queryresult.add(queryId + "  0 " + doc.get("ID")  + "    0 " + hits[i].score +  " " + "ENGLISH");
            } else {
                queryresult.add((i+1) + ". " + "No path for this document");
            }
        }
        return queryresult;
    }
}