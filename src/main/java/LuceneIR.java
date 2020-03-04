public class LuceneIR {
    public static void main(String[] args) throws Exception {
        Parser parseCranfield = new Parser();
        Indexer indexDocuments = new Indexer();
        Searcher searchIndex = new Searcher();
        System.out.println("Parsing the 1400 Cranfield files into Individual Documents");
        parseCranfield.PraseCran1400();
        System.out.println("Indexing the parsed Documents using BM25 and English Analyzer.");
        indexDocuments.IndexCran();
        System.out.println("Querying the Index from the cran.qry file and writing the results to result.txt...");
        searchIndex.searchCran();
        System.out.println("Searching complete, output written to result.txt.");
    }
}