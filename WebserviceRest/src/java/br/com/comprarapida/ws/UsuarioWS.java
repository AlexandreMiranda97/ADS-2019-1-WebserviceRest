/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.comprarapida.ws;

import br.com.comprarapida.daos.UsuarioDAO;
import br.com.comprarapida.entidades.Usuario;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("usuario")
public class UsuarioWS {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@FormParam("nome") String nome, @FormParam("email") String email, @FormParam("senha") String senha) {
        try {
            //validar campos obrigatórios
            if (nome.isEmpty()) {
                return Response.status(Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").encoding("Parâmetros incorretos!").build();//CORS 
            }
            //persistir os dados
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            if (usuarioDAO.save(usuario) != 0) {
                return Response.status(Status.OK).header("Access-Control-Allow-Origin", "*").build();//CORS 
            } else {
                return Response.status(Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Usuario usuario) {
        try {
            //validar campos obrigatórios
            //persistir os dados
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            if (usuarioDAO.save(usuario) != 0) {
                return Response.status(Status.OK).entity(usuario).build();
            } else {
                return Response.status(Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response read(@PathParam("id") int id) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = null;
            if ((usuario = usuarioDAO.select(id)) != null) {
                return Response.status(Status.OK).entity(usuario).build();
            } else {
                return Response.status(Status.NOT_FOUND).build();
            }
        } catch (SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response reads(@QueryParam("email") String email) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            List<Usuario> usuarios = usuarioDAO.selects(email);
            //GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(usuarios) {};
            return Response.status(Status.OK).header("Access-Control-Allow-Origin", "*").entity(usuarios).build();
        } catch (SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(Usuario usuario) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            if (usuarioDAO.update(usuario) != 0) {
                return Response.status(Status.OK).entity(usuario).build();
            } else {
                return Response.status(Status.NOT_FOUND).build();
            }
        } catch (SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = null;
            if ((usuario = usuarioDAO.delete(id)) != null) {
                return Response.status(Status.OK).entity(usuario).build();
            } else {
                return Response.status(Status.NOT_FOUND).build();
            }
        } catch (SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
