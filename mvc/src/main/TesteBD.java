package main;

import model.Estudante;
import model.EstudanteBD;

public class TesteBD {

	static void tentar(EstudanteBD bd, String nome, String mat, String tel) {

		Estudante e = new Estudante();

		e.setNome(nome);
		e.setMatricula(mat);
		e.setTelefone(tel);

		try {

			bd.salvar(e);

			System.out.println(
					"Gravado : " + nome + " / " + mat + " (total: " + bd.contar() + ")");

		} catch (IllegalArgumentException erro) {

			System.out.println(
					"Recusado: " + nome + " / " + mat + " -> " + erro.getMessage());
		}
	}

	public static void main(String[] args) {

		EstudanteBD bd = new EstudanteBD();

		tentar(bd, "Ana Souza", "2026001", "47999990000");
		tentar(bd, "Bruno Lima", "2026002", "");
		tentar(bd, "", "2026003", "");
		tentar(bd, "Carla Nunes", "", "");
		tentar(bd, "Diego Alves", "2026001", "");
		tentar(bd, "Maria O'Brien", "2026004", "4733220000");

		System.out.println("--- no banco agora ---");

		for (Estudante e : bd.listarTodos()) {

			System.out.println(
					" " + e.getNome() + " | " + e.getMatricula() + " | " + e.getTelefone());
		}
	}
}