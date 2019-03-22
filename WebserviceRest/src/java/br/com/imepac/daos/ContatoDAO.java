/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imepac.daos;

import br.com.imepac.entidades.Contato;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class ContatoDAO {

    private Connection conexao;
    private String url = "jdbc:mysql://localhost/agenda"; //Nome da base de dados
    private String user = "root"; //nome do usuário do MySQL
    private String password = ""; //senha do MySQL

    public ContatoDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexao = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ContatoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int save(Contato contato) throws SQLException {
        String sql = "INSERT INTO contato VALUES (null,?,?)";
        PreparedStatement preparedStatement;
        preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, contato.getNome());
        preparedStatement.setString(2, contato.getEmail());
        return preparedStatement.executeUpdate();
    }

    public int update(Contato contato) throws SQLException {
        String sql = "UPDATE contato SET  nome = ?, email = ? WHERE id = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setString(1, contato.getNome());
        preparedStatement.setString(2, contato.getEmail());
        preparedStatement.setInt(3, contato.getId());
        return preparedStatement.executeUpdate();
    }

    public List<Contato> selects() throws SQLException {
        String sql = "SELECT * FROM contato";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Contato> results = new ArrayList();
        while (resultSet.next()) {
            Contato contato = new Contato();
            contato.setId(resultSet.getInt("id"));
            contato.setNome(resultSet.getString("nome"));
            contato.setEmail(resultSet.getString("email"));
            results.add(contato);
        }
        return results;
    }

    public Contato select(int id) throws SQLException {
        String sql = "SELECT * FROM contato WHERE id = ?";
        PreparedStatement preparedStatement = conexao.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Contato contato = null;
        if (resultSet.next()) {
            contato = new Contato();
            contato.setId(resultSet.getInt("id"));
            contato.setNome(resultSet.getString("nome"));
            contato.setEmail(resultSet.getString("email"));
            return contato;
        } else {
            return null;
        }
    }

    public boolean closeConnection() {
        try {
            conexao.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContatoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Contato delete(int id) throws SQLException {
        Contato contato = this.select(id);
        if (contato != null) {
            String sql = "DELETE FROM contato WHERE id = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            return contato;
        } else {
            return null;
        }
    }

    public boolean delete(Contato contato) throws SQLException {
        if (contato != null) {
            String sql = "DELETE FROM contato WHERE id = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, contato.getId());
            preparedStatement.execute();
            return true;
        } else {
            return false;
        }
    }
}

