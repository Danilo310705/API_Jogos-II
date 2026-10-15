package apiJogos.repository;

import apiJogos.model.Jogos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface JogosRepository extends JpaRepository<Jogos, Long> {

    // @Query permite criar uma consulta personalizada usando JPQL
    // Os filtros são opcionais e podem ser combinados
    @Query("""
        SELECT j FROM Jogos j
        WHERE (:nome IS NULL OR
               LOWER(j.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
          AND (:genero IS NULL OR
               LOWER(j.genero) = LOWER(:genero))
          AND (:dificuldade IS NULL OR
               LOWER(j.dificuldade) = LOWER(:dificuldade))
    """)

    // Recebe os três filtros e devolve os jogos que correspondem à pesquisa
    List<Jogos> filtrar(
            @Param("nome") String nome,
            @Param("genero") String genero,
            @Param("dificuldade") String dificuldade
    );
}
