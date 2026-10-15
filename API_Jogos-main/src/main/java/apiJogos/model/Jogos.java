package apiJogos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity // Informa que esta classe representa uma entidade do banco
@Table(name = "jogos") // Define o nome da tabela no PostgreSQL
public class Jogos {

    @Id // Identifica a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco gera o ID automaticamente
    private Long id;

    private String nome;
    private String genero;
    private String dificuldade;
    private Integer ano_jogado;
}
