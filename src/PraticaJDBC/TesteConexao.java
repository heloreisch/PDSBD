package PraticaJDBC;

import java.sql.Connection;
import java.sql.SQLException;

import dao.Conexao;

public class TesteConexao {

    public static void main(String[] args) {

        try (Connection con =
                Conexao.abrir()) {

            System.out.println(
                "Conexao estabelecida."
            );

            System.out.println(
                "Servidor : " +
                con.getMetaData()
                   .getDatabaseProductName()
            );

            System.out.println(
                "Banco : " +
                con.getCatalog()
            );

        } catch (SQLException e) {

            System.out.println(
                "Falha na conexao: " +
                e.getMessage()
            );
        }
    }
}