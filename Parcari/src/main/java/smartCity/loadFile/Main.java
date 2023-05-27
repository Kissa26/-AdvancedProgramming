package smartCity.loadFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        try {
            Database.createConnection();
            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(
                    "insert into locuri(id_loc,id_parcare) values (?,?)");

            File file=new File("C:\\Users\\teodo\\OneDrive\\Desktop\\facultate 2\\proiect\\Parcari\\src\\main\\resources\\parcari.csv");
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            String line;
            String[] columns;
            while((line=br.readLine())!=null)
            {
                columns = line.split(",");
                int id_loc = parseInt(columns[0]);
                pstmt.setInt(1,id_loc);
                int id_parcare = parseInt(columns[1]);
                pstmt.setInt(2,id_parcare);
                pstmt.executeUpdate();
            }
            fr.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
