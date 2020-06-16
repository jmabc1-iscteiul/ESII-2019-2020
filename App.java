package Grupo_2.p5;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.apache.commons.lang3.StringUtils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * ESII - Complemento Projeto - Grupo 2 - Ponto 5
 *
 */


public class App {


	//Localização do ficheiro RDF
	static File localDirectory = new File("/ESII-GIT-REPOSITORY/");

	//Nome do ficheiro
	static String fileName = "covid19spreading.rdf";

	//Localização do repositorio
	static String gitPathURI = "https://github.com/vbasto-iscte/ESII1920.git";

	//Array que vai receber todas as regioes
	static final ArrayList<Regiao> regioes = new ArrayList<Regiao>();


	public static void main(String[] args)  throws InvalidRemoteException, TransportException, GitAPIException {

		//Recebe os argumentos e corre a query
		String tipoDados = args[0];
		String tipoOperacao = args[1];
		Double valor = Double.parseDouble(args[2]);
		

		//Procedimento que faz a Clonagem do Repositorio GIT
		CloneGITRepository();

		try {

			//Inicializa as variaveis a utilziar nas queries
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = null;

			//Cria um DOCUMENT a partir do ficheiro
			Document doc = readRDFFile(localDirectory+"/"+fileName);

			//Cria o Nodelist a partir do Document
			NodeList nl = getNodeList(doc);

			//Cria as regioes encontradas no NodeList
			getRegioes(nl);

			//Coloca nas regioes os valores encontrados
			setRegioesValues(xpathFactory, xpath, expr, doc );

			//Apresenta os resultados obtidos
			for(int i=0; i< regioes.size(); i++) {
				System.out.println(regioes.get(i).toString());
			}

			//Executa a operação solicitada nos argumentos
			switch(tipoDados){
			case "Infecoes": {
				switch(tipoOperacao){
				case "Maior": {
					for(int i=0; i < regioes.size(); i++) {
						if (regioes.get(i).getInfecoes()>=valor) System.out.println("Query Infecoes ("+tipoOperacao+" "+valor+"): "+regioes.get(i).getNomeRegiao());
					}
				}break;
				case "Menor": {
					for(int i=0; i < regioes.size(); i++) {
						if (regioes.get(i).getInfecoes()<=valor) System.out.println("Query Infecoes ("+tipoOperacao+" "+valor+"): "+regioes.get(i).getNomeRegiao());
					}
				}break;
				}
			}break;
			case "Internamentos": {
				switch(tipoOperacao){
				case "Maior": {
					for(int i=0; i < regioes.size(); i++) {
						if (regioes.get(i).getInternamentos()>=valor) System.out.println("Query Internamentos ("+tipoOperacao+" "+valor+"): "+regioes.get(i).getNomeRegiao());
					}
				}break;
				case "Menor": {
					for(int i=0; i < regioes.size(); i++) {
						if (regioes.get(i).getInternamentos()<=valor) System.out.println("Query Internamentos ("+tipoOperacao+" "+valor+"): "+regioes.get(i).getNomeRegiao());
					}
				}break;
				}
			}break;
			case "Testes": {
				switch(tipoOperacao){
				case "Maior": {
					for(int i=0; i < regioes.size(); i++) {
						if (regioes.get(i).getTestes()>=valor) System.out.println("Query Testes ("+tipoOperacao+" "+valor+"): "+regioes.get(i).getNomeRegiao());
					}
				}break;
				case "Menor": {
					for(int i=0; i < regioes.size(); i++) {
						if (regioes.get(i).getTestes()<=valor) System.out.println("Query Testes ("+tipoOperacao+" "+valor+"): "+regioes.get(i).getNomeRegiao());
					}
				}break;
				}
			}break;
			}

		} catch (Exception e) { e.printStackTrace(); }
	}

	//FUNCAO QUE FAZ A QUERY AOS RESULTADOS DAS INFECOES
	public static void queryResultsInfecoes(ArrayList<Regiao> regioes, String operacao, Double valor) {

		switch(operacao){
		case "maiorDoque": {
			for(int i=0; i < regioes.size(); i++) {
				if (regioes.get(i).getInfecoes()>=valor) System.out.println("Query Infecoes ("+operacao+" "+valor+"): "+regioes.get(i).getNomeRegiao());
			}
		}	break;	

		case "menorDoque": {
			for(int i=0; i < regioes.size(); i++) {
				if (regioes.get(i).getInfecoes()<=valor) System.out.println("Query Infecoes ("+operacao+" "+valor+"): "+regioes.get(i).getNomeRegiao());
			}
		}break;
		default: {
			System.out.println("Nenhum resultado obtido!");
		}
		}
	}

	//FUNCAO QUE FAZ A QUERY AOS RESULTADOS DOS INTERNAMENTOS
	public static void queryResultsInternamentos(ArrayList<Regiao> regioes, String operacao, Double valor) {

		switch(operacao){
		case "maiorDoque": {
			for(int i=0; i < regioes.size(); i++) {
				if (regioes.get(i).getInternamentos()>=valor) System.out.println("Query Internamentos ("+operacao+" "+valor+"): "+regioes.get(i).getNomeRegiao());
			}
		}break;

		case "menorDoque": {
			for(int i=0; i < regioes.size(); i++) {
				if (regioes.get(i).getInternamentos()<=valor) System.out.println("Query Internamentos ("+operacao+" "+valor+"): "+regioes.get(i).getNomeRegiao());
			}
		}break;
		default: {
			System.out.println("Nenhum resultado obtido!");
		}
		}
	}

	//FUNCAO QUE FAZ A QUERY AOS RESULTADOS DOS TESTES
	public static void queryResultsTestes(ArrayList<Regiao> regioes, String operacao, Double valor) {

		switch(operacao){
		case "maiorDoque": {
			for(int i=0; i < regioes.size(); i++) {
				if (regioes.get(i).getTestes()>=valor) System.out.println("Query Testes ("+operacao+" "+valor+"): "+regioes.get(i).getNomeRegiao());
			}
		}break;

		case "menorDoque": {
			for(int i=0; i < regioes.size(); i++) {
				if (regioes.get(i).getTestes()<=valor) System.out.println("Query Testes ("+operacao+" "+valor+"): "+regioes.get(i).getNomeRegiao());
			}
		}break;
		default: {
			System.out.println("Nenhum resultado obtido!");
		}
		}

	}

	//FUNCAO QUE OBTEM OS VALUES DAS REGIOES
	static void setRegioesValues(XPathFactory xpathFactory, XPath xpath, XPathExpression expr, Document doc) throws XPathExpressionException {

		//Obtem os valores de cada Regiao e coloca-os nos objetos Regiao
		for (int i= 0; i<regioes.size(); i++) {
			xpathFactory = XPathFactory.newInstance();
			xpath = xpathFactory.newXPath();
			String query=null;
			//Infecoes
			query = "//*[contains(@about,'"+regioes.get(i).getNomeRegiao()+"')]/Infecoes/text()";  
			expr = xpath.compile(query);
			regioes.get(i).setInfecoes((Double) expr.evaluate(doc,XPathConstants.NUMBER));
			//Internamentos
			query = "//*[contains(@about,'"+regioes.get(i).getNomeRegiao()+"')]/Internamentos/text()";  
			expr = xpath.compile(query);
			regioes.get(i).setInternamentos((Double) expr.evaluate(doc,XPathConstants.NUMBER));
			//Testes
			query = "//*[contains(@about,'"+regioes.get(i).getNomeRegiao()+"')]/Testes/text()";  
			expr = xpath.compile(query);
			regioes.get(i).setTestes((Double) expr.evaluate(doc,XPathConstants.NUMBER));
		}
	}

	//FUNCAO QUE PEGA NO NL E CRIA AS REGIOES
	static void getRegioes(NodeList nl) {
		//Processa os Nodes de Regioes e guarda no ArrayList de Regioes
		for (int i = 0; i < nl.getLength(); i++) {
			//Addiciona os items encontrados à lista
			regioes.add(new Regiao(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"), 0.0, 0.0, 0.0));
		}
	}

	//FUNCAO QUE PEGA NO DOC E CRIA UMA NODELIST
	public static NodeList getNodeList(Document doc) throws XPathExpressionException {
		//Query para consultar as regioes e colocar num NodeList
		String query = "/RDF/NamedIndividual/@*";
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		XPathExpression expr = xpath.compile(query);         
		return (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	}

	//FUNCAO QUE LE O FICHEIRO RDF E COLOCA-O NUM DOCUMENT
	public static Document readRDFFile(String filename) throws ParserConfigurationException, SAXException, IOException {
		//Abre o ficheiro RDF
		System.out.println("A abrir o ficheiro: "+ fileName+" !");
		File inputFile = new File(localDirectory+"/"+fileName);    	      	  
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();
		return doc;
	}

	//FUNCAO QUE ACEDE AO GIT E FAZ O CLONE
	public static void CloneGITRepository() throws InvalidRemoteException, TransportException, GitAPIException  {

		//Verifica se a diretoria existe e limpa o seu conteudo
		if (localDirectory.exists()) {
			try {
				FileUtils.cleanDirectory(localDirectory); 
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//Acede ao Repositorio e faz o push ao conteudo
		Git.cloneRepository()
		.setURI(gitPathURI)
		.setDirectory(localDirectory) // #1
		.call();
		System.out.println("PUSH ao repositorio efectuado!");
	}
}

