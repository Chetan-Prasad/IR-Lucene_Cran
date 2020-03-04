import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Parser {
    public void PraseCran1400() throws Exception {
        String inputFile="cran.all.1400";
        BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
        String line;
        String tag = null;
        StringBuilder ib = new StringBuilder();
        StringBuilder tb = new StringBuilder();
        StringBuilder ab = new StringBuilder();
        StringBuilder bb = new StringBuilder();
        StringBuilder wb = new StringBuilder();
        int count=1;
        try {
            while((line = br.readLine()) != null){
                if(line.startsWith(".I")) {
                    tag = ".I";
                }
                else if (line.startsWith(".T")) {
                    tag = ".T";
                }
                else if (line.startsWith(".A")) {
                    tag = ".A";
                }
                else if (line.startsWith(".B")) {
                    tag = ".B";
                }
                else if (line.startsWith(".W")) {
                    tag = ".W";
                }

                if (line.startsWith(".I")) {
                    if (ib.length() != 0) {
                        File file = new File("Documents/DOC_ID_" + count + ".txt");
                        if (file.createNewFile()) {
                            ib.append(tb);
                            ib.append(ab);
                            ib.append(bb);
                            ib.append(wb);

                            PrintWriter writer = new PrintWriter(file, "UTF-8");
                            writer.println(ib.toString());
                            writer.close();
                            ib.delete(0, ib.length());
                            tb.delete(0, tb.length());
                            ab.delete(0, ab.length());
                            bb.delete(0, bb.length());
                            wb.delete(0, wb.length());
                            count++;
                        }
                    }
                    ib.append(line+" ");
                }
                else if (line.startsWith(".T")||tag == ".T") {
                    if (tb.length() != 0 && line.startsWith(".T"))
                        continue;
                    tb.append(line+" ");
                    tag = ".T";
                }
                else if (line.startsWith(".A") || tag == ".A") {
                    if (ab.length() != 0 && line.startsWith(".A"))
                        continue;
                    ab.append(line+" ");
                    tag = ".A";
                }
                else if (line.startsWith(".B") || tag == ".B") {
                    if (bb.length() != 0 && line.startsWith(".B"))
                        continue;
                    bb.append(line+" ");
                    tag = ".B";
                }
                else if (line.startsWith(".W") || tag == ".W") {
                    if (wb.length() != 0 && line.startsWith(".W"))
                        continue;
                    wb.append(line+" ");
                    tag = ".W";
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (ib.length() != 0) {
                File file = new File("Documents/DOC_ID_" + count + ".txt");
                if (file.createNewFile()) {
                    ib.append(tb);
                    ib.append(ab);
                    ib.append(bb);
                    ib.append(wb);

                    PrintWriter writer = new PrintWriter(file, "UTF-8");
                    writer.println(ib.toString());
                    writer.close();
                    ib.delete(0, ib.length());
                    count++;
                }
            }
            br.close();
        }
    }
}