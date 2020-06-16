package Grupo_2.p5;

public class Regiao {


	private String nomeRegiao;
	private Double infecoes;
	private Double internamentos;
	private Double testes;

	public Regiao(String nomeRegiao, Double infecoes, Double internamentos, Double testes) {

		this.nomeRegiao = nomeRegiao;
		this.infecoes = infecoes;
		this.internamentos = internamentos;
		this.testes = testes;
	}

	public String getNomeRegiao() {
		return this.nomeRegiao;
	}

	public Double getInfecoes() {
		return this.infecoes;
	}

	public Double getInternamentos() {
		return this.internamentos;
	}

	public Double getTestes() {
		return this.testes;
	}

	public void setInfecoes(Double infecoes) {
		this.infecoes=infecoes;
	}

	public void setInternamentos(Double internamentos) {
		this.internamentos=internamentos;
	}

	public void setTestes(Double testes) {
		this.testes=testes;
	}

	public String toString() {
		return "Regiao: "+getNomeRegiao()+"\n  Infecoes: "+getInfecoes()+" / Internamentos: "+getInternamentos()+" / Testes: "+getTestes()+"\n";		
	}
}
