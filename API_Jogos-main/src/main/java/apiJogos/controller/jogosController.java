package apiJogos.controller;


import apiJogos.model.Jogos;
import apiJogos.repository.JogosRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/jogos")
public class jogosController {

    private final JogosRepository jogosRepository; //final informa que essa referência não poderá ser substituída depois de inicializada.

    public jogosController(JogosRepository jogosRepository) {
        this.jogosRepository = jogosRepository;
    } // Recebe o repository que será utilizado para acessar o banco

    @GetMapping("/Listar_jogos")
    public ResponseEntity<List<Jogos>> listarJogos() {//puxa o banco de dados
        return ResponseEntity.ok(jogosRepository.findAll()); //solicita ao repositório todos os jogos armazenados no banco de dados
    }

    @GetMapping("/Buscar_ID/{id}")
    public ResponseEntity<Jogos> BuscaID(@PathVariable Long id){// define que a busca será realizada pelo ID
        // findById pesquisa no banco um jogo com o ID informado
        // orElse(null) retorna null caso não encontre nenhum jogo
        Jogos jogo = jogosRepository.findById(id).orElse(null);

        // Verifica se o jogo foi encontrado no banco
        if (jogo != null) {
            return ResponseEntity.ok(jogo); // Retorna o jogo encontrado (HTTP 200)
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<Jogos>> BuscarFiltro(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String dificuldade) {

        // Chama o metodo filtrar do repository
        // Envia os três filtros informados pelo usuário
        // O PostgreSQL realizará a pesquisa dos registros
        List<Jogos> resultado = jogosRepository.filtrar(
                nome,
                genero,
                dificuldade
        );

        // Retorna com a lista dos jogos encontrados
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/gravar")
    public ResponseEntity<Jogos> gravarJogo(@RequestBody Jogos jogos){ //metodo para gravar um novo jogo
        // Define o Id como null para que o banco gere automaticamente um novo Id
        jogos.setId(null);

        // O metodo save salva o jogo no PostgreSQL
        // A variável jogoSalvo recebe o jogo com o ID gerado pelo banco
        Jogos jogoSalvo = jogosRepository.save(jogos);

        // Retorna e mostra os dados do jogo cadastrado
        return ResponseEntity.status(201).body(jogoSalvo);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Jogos> atualizar(@PathVariable Long id, @RequestBody Jogos jogoAtualizado) {

        // Pesquisa no banco um jogo com o ID informado
        Jogos jogo = jogosRepository.findById(id).orElse(null);

        // Verifica se o jogo existe
        if (jogo != null) {

            // Atualiza os atributos do jogo encontrado
            jogo.setNome(jogoAtualizado.getNome()); // Altera o nome
            jogo.setGenero(jogoAtualizado.getGenero()); // Altera o gênero
            jogo.setDificuldade(jogoAtualizado.getDificuldade()); // Altera a dificuldade
            jogo.setAno_jogado(jogoAtualizado.getAno_jogado()); // Altera o ano jogado

            // Salva as alterações no PostgreSQL
            Jogos jogoSalvo = jogosRepository.save(jogo);

            // Retorna os dados atualizados
            return ResponseEntity.ok(jogoSalvo);
        }

        // Caso o ID não exista no banco, retorna
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {//metodo para exluir um jogo

        // Verifica se existe um jogo com o ID informado
        if (jogosRepository.existsById(id)) {

            // Exclui do PostgreSQL o jogo correspondente ao ID
            jogosRepository.deleteById(id);

            // Retorna indicando exclusão realizada
            return ResponseEntity.noContent().build();
        }

        // Caso o ID não exista, retorna HTTP 404
        return ResponseEntity.notFound().build();
    }
}
//Vinicius Budack 60004996
//Danilo Sanches 60004975
//Isabela Candido 60004968

