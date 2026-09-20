package model;

/**
 * MODEL - so os dados de um estudante.
 *
 * Nao tem import nenhum. Nao sabe que existe tela nem banco.
 */
public class Estudante {

	private String nome;
	private String matricula;
	private String telefone;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		// this.nome = o ATRIBUTO; nome sozinho = o PARAMETRO que chegou.
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}