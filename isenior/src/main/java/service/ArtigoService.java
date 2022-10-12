package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.ArtigoDAO;
import model.Artigo;
import spark.Request;
import spark.Response;


public class ArtigoService {

	private ArtigoDAO usuarioDAO = new ArtigoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID_ARTIGO = 1;
	private final int FORM_ORDERBY_CATEGORIA = 2;
	private final int FORM_ORDERBY_TITULO = 3;
	
	
	public ArtigoService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Artigo(), FORM_ORDERBY_CATEGORIA);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Artigo(), orderBy);
	}

	
	public void makeForm(int tipo, Artigo artigo, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umArtigo = "";
		if(tipo != FORM_INSERT) {
			umArtigo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umArtigo += "\t\t<tr>";
			umArtigo += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/artigo/list/1\">Novo Artigo</a></b></font></td>";
			umArtigo += "\t\t</tr>";
			umArtigo += "\t</table>";
			umArtigo += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/artigo/";
			String name, titulo, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Artigo";
				titulo = "";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + artigo.getIdArtigo();
				name = "Atualizar Artigo (ID " + artigo.getIdArtigo() + ")";
				titulo = artigo.getTitulo();
				buttonLabel = "Atualizar";
			}
			umArtigo += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umArtigo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umArtigo += "\t\t<tr>";
			umArtigo += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umArtigo += "\t\t</tr>";
			umArtigo += "\t\t<tr>";
			umArtigo += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umArtigo += "\t\t</tr>";
			umArtigo += "\t\t<tr>";
			umArtigo += "\t\t\t<td>&nbsp;Titulo do Artigo: <input class=\"input--register\" type=\"text\" name=\"titulo\" value=\""+ titulo +"\"></td>";
			umArtigo += "\t\t\t<td>Categoria: <input class=\"input--register\" type=\"text\" name=\"categoria\" value=\""+ artigo.getCategoria() +"\"></td>";
			umArtigo += "\t\t\t<td>Tag: <input class=\"input--register\" type=\"text\" name=\"tag\" value=\""+ artigo.getTag() +"\"></td>";
			umArtigo += "\t\t</tr>";
			umArtigo += "\t\t<tr>";
			umArtigo += "\t\t\t<td>&nbsp;Palavra Chave: <input class=\"input--register\" type=\"text\" name=\"epalavraChave\" value=\""+ artigo.getPalavraChave().toString() + "\"></td>";
			umArtigo += "\t\t\t<td>Texto: <input class=\"input--register\" type=\"text\" name=\"texto\" value=\""+ artigo.getTexto().toString() + "\"></td>";
			umArtigo += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umArtigo += "\t\t</tr>";
			umArtigo += "\t</table>";
			umArtigo += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umArtigo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umArtigo += "\t\t<tr>";
			umArtigo += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Usuario (ID " + artigo.getIdUsuario() + ")</b></font></td>";
			umArtigo += "\t\t</tr>";
			umArtigo += "\t\t<tr>";
			umArtigo += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umArtigo += "\t\t</tr>";
			umArtigo += "\t\t<tr>";
			umArtigo += "\t\t\t<td>&nbsp;Titulo do Usuario: "+ artigo.getTitulo() +"</td>";
			umArtigo += "\t\t\t<td>Categoria: "+ artigo.getCategoria() +"</td>";
			umArtigo += "\t\t\t<td>Tag: "+ artigo.getTag() +"</td>";
			umArtigo += "\t\t</tr>";
			umArtigo += "\t\t<tr>";
			umArtigo += "\t\t\t<td>&nbsp;Palavras Chave: "+ artigo.getPalavraChave().toString() + "</td>";
			umArtigo += "\t\t\t<td>Texto: "+ artigo.getTexto().toString() + "</td>";
			umArtigo += "\t\t\t<td>&nbsp;</td>";
			umArtigo += "\t\t</tr>";
			umArtigo += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-ARTIGO>", umArtigo);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Catálogo de Artigos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/artigo/list/" + FORM_ORDERBY_ID_ARTIGO + "\"><b>Id Artigo</b></a></td>\n" +
        		"\t<td><a href=\"/artigo/list/" + FORM_ORDERBY_CATEGORIA + "\"><b>Categoria</b></a></td>\n" +
        		"\t<td><a href=\"/artigo/list/" + FORM_ORDERBY_TITULO + "\"><b>Titulo</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Artigo> artigos;
		if (orderBy == FORM_ORDERBY_ID_ARTIGO) {                 	artigos = artigoDAO.getOrderByIdArtigo();
		} else if (orderBy == FORM_ORDERBY_CATEGORIA) {		artigos = artigoDAO.getOrderByCategoria();
		} else if (orderBy == FORM_ORDERBY_TITULO) {			artigos = artigoDAO.getOrderByTitulo();
		} else {											artigos = artigoDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Artigo a : artigos) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + a.getIdArtigo() + "</td>\n" +
            		  "\t<td>" + a.getCategoria() + "</td>\n" +
            		  "\t<td>" + a.getTitulo() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/artigo/" + a.getIdArtigo() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/artigo/update/" + a.getIdArtigo() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteUsuario('" + a.getIdArtigo() + "', '" + a.getTitulo() + "', '" + a.getCategoria() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-ARTIGO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String titulo = request.queryParams("titulo");
		String tag = request.queryParams("tag");
		String sobrenome = request.queryParams("sobrenome");
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");
		
		String resp = "";
		
		Usuario usuario = new Usuario(-1, email, nomeUsuario, senha, primeiroNome, sobrenome);
		
		if(usuarioDAO.insert(usuario) == true) {
            resp = "Usuário (" + nomeUsuario + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Usuário (" + nomeUsuario + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int idUsuario = Integer.parseInt(request.params(":idUsuario"));		
		Usuario usuario = (Usuario) usuarioDAO.get(idUsuario);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, usuario, FORM_ORDERBY_NOME_USUARIO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuário " + idUsuario + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int idUsuario = Integer.parseInt(request.params(":idUsuario"));		
		Usuario usuario = (Usuario) usuarioDAO.get(idUsuario);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_NOME_USUARIO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuário " + idUsuario + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int idUsuario = Integer.parseInt(request.params(":idUsuario"));
		Usuario usuario = usuarioDAO.get(idUsuario);
        String resp = "";       

        if (usuario != null) {
        	usuario.setEmail(request.queryParams("email"));
        	usuario.setNomeUsuario(request.queryParams("nomeUsuario"));
        	usuario.setSenha(request.queryParams("senha"));
        	usuario.setPrimeiroNome(request.queryParams("primeiroNome"));
        	usuario.setSobrenome(request.queryParams("sobrenome"));
        	usuarioDAO.update(usuario);
        	response.status(200); // success
            resp = "Usuário (ID " + usuario.getIdUsuario() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuário (ID \" + usuario.getIdUsuario() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int idUsuario = Integer.parseInt(request.params("idUsuario"));
        Usuario usuario = usuarioDAO.get(idUsuario);
        String resp = "";       

        if (usuario != null) {
            usuarioDAO.delete(idUsuario);
            response.status(200); // success
            resp = "Usuário (" + idUsuario + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuário (" + idUsuario + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}