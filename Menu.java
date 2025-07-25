
import java.util.List;
import java.util.Scanner;
import controllers.GerenciadorUsuarios;
import models.Status;
import models.Tarefa;
import models.Usuario;
import models.Categoria;
import models.Prioridade;
import controllers.GerenciadorTarefas;


public class Menu {
    
    // Códigos ANSI para as cores
    public static final String RESET = "\u001B[0m";
    public static final String VERDE = "\u001B[32m";
    public static final String VERMELHO = "\u001B[31m";
    public static final String MARROM_ESCURO = "\u001B[38;5;94m";    // marrom escuro
    public static final String MARROM_CLARO = "\u001B[38;5;180m";   // marrom claro dourado
    public static final String BEGE = "\u001B[38;5;223m"; 

    //cor da letra pra variaveis na classe static
    private static final String REDP = "\u001B[31m";
    public static final String RESETP = "\u001B[0m";
    private static final String GREENP = "\u001B[32m";
    


    private static GerenciadorUsuarios usuario = new GerenciadorUsuarios();
    private static Scanner scanner = new Scanner(System.in);
    private static GerenciadorTarefas tarefas = new GerenciadorTarefas(); // add tarefa


    public  void exibirMenu() {
        int opcao;

        do {  
            System.out.println(MARROM_ESCURO + "╔════════════════════════════════════════════╗" + RESET);
            System.out.println(MARROM_ESCURO + "║            Gerenciador de Tarefas          ║" + RESET);
            System.out.println(MARROM_ESCURO + "╚════════════════════════════════════════════╝" + RESET);
            System.out.println();

            System.out.println(MARROM_CLARO + "                    MENU" + RESET);
            System.out.println(MARROM_CLARO + "══════════════════════════════════════════════" + RESET);
            System.out.println(BEGE + "1. Adicionar Usuário" + RESET);
            System.out.println(BEGE + "2. Listar Usuários" + RESET);
            System.out.println(BEGE + "3. Remover Usuário" + RESET);
            System.out.println(BEGE + "4. Adicionar Tarefa" + RESET);
            System.out.println(BEGE + "5. Remover Tarefa" + RESET);
            System.out.println(BEGE + "6. Listar Tarefas" + RESET);
            System.out.println(BEGE + "7. Atualizar Status da Tarefa" + RESET);
            System.out.println(BEGE + "8. Atualizar Prioridade da Tarefa" + RESET);
            System.out.println(BEGE + "0. Sair" + RESET);
            System.out.println(MARROM_CLARO + "══════════════════════════════════════════════" + RESET);
            System.out.print(MARROM_CLARO + "Escolha uma opção: " + RESET);
            opcao = scanner.nextInt();

                switch (opcao){
                    case 1 -> adicionarUsuario();
                    case 2 -> listarUsuarios();
                    case 3 -> removerUsuario();
                    case 4 -> adicionarTarefas();
                    case 5 -> removerTarefa();
                    case 6 -> listartarefas();
                    case 7 -> atualizarStatus();
                    case 8 -> atualizarPrioridadeTarefa();
                    case 0 -> System.out.println("Saindo do menu...");
                    default -> System.out.println(REDP + "Opção inválida. Tente novamente."+ RESET);
                }
            } while (opcao != 0);
        }
    
        private static void adicionarUsuario() {
            System.out.print("Digite o nome do usuário: ");
            String nome = scanner.next();
            usuario.adicionarUsuario(nome);
            System.out.println(GREENP+ "Usuário adicionado com sucesso!"+ RESETP);
        }
        private static void listarUsuarios() {
            System.out.println( "Lista de Usuários:" );
            for (Usuario u : usuario.listarUsuarios()) {
                System.out.println("- " + u.getName());
            }
        }
        private static void removerUsuario() {
            System.out.print("Digite o nome do usuário a ser removido: ");
            String nome = scanner.next();
            if (usuario.removerUsuario(nome)) {
                System.out.println(GREENP+ "Usuário removido com sucesso!"+RESETP);
            } else {
                System.out.println(REDP+ "Usuário não encontrado."+RESETP);
            }
        }
        ////// adicionar tarefa
        private static void adicionarTarefas() {
    System.out.print("Digite seu nome: ");
    String nome = scanner.next();

    try {
        Usuario usuarioEncontrado = usuario.buscarUsuarioPorNome(nome);
        scanner.nextLine(); // consumir \n pendente
        System.out.print("Digite o título da tarefa: ");
        String titulo = scanner.nextLine();
        System.out.print("Digite a descrição da tarefa: ");
        String descricao = scanner.nextLine();
        System.out.print("Digite a categoria da tarefa: ");
        String categoria = scanner.nextLine();
        tarefas.adicionarTarefa(titulo, descricao, usuarioEncontrado, categoria);
        System.out.println(GREENP + "Tarefa adicionada com sucesso!" + RESETP);
    } catch (exceptions.UsuarioNaoEncontradoException e) {
        System.out.println(REDP + e.getMessage() + RESETP);
    }
}

        ///remover tarefa
       private static void removerTarefa() {
    System.out.println("Digite seu nome:");
    String nome = scanner.next();
    try {
        Usuario usuarioEncontrado = usuario.buscarUsuarioPorNome(nome);
           List<Tarefa> lista = tarefas.listartarefas();
        for (int i = 0; i < lista.size(); i++) {
            System.out.print(" [ " + i + " ] ");
            lista.get(i).mostrarDetalhes();
        }
        System.out.print("Digite o número da tarefa que deseja remover: ");
        int indice = scanner.nextInt();
        boolean sucesso = tarefas.removerTarefaPorIndice(indice, usuarioEncontrado);
        if (sucesso) {
            System.out.println(GREENP + "Tarefa removida com sucesso!" + RESETP);
        } else {
            System.out.println(REDP + "Índice inválido ou tarefa não pertence ao usuário." + RESETP);
        }
    } catch (exceptions.UsuarioNaoEncontradoException e) {
        System.out.println(REDP + e.getMessage() + RESETP);
    }
}

        private static void listartarefas(){
            for(Tarefa t: tarefas.listartarefas()){
                t.mostrarDetalhes();
            }
            if (tarefas.listartarefas().isEmpty()) {
                System.out.println("Nenhuma tarefa encontrada.");
            } else {
                System.out.println("Total de tarefas: " + tarefas.listartarefas().size());
            }
        }
        public static void atualizarStatus(){
            System.out.println("Digite o titulo da tarefa que vai ser atualizada:");
            var titulo = scanner.next();
            System.out.println("Digite o novo status da tarefa (1 - Pendente, 2 - Em Andamento, 3 - Concluída):");
            var status = scanner.nextInt();
            var novoStatus = switch (status){
                case 1 -> Status.PENDENTE;
                case 2 -> Status.EM_ANDAMENTO;
                case 3 -> Status.CONCLUIDA;
                default -> Status.PENDENTE;
            };
            if (tarefas.atualizarStatus(titulo,novoStatus )){
                System.out.println((GREENP+ "Status atualizado." + RESETP));
            }else{
                System.out.println(REDP+ "Tarefa não encontrada." + RESETP);
            }
        }

        public static void atualizarPrioridadeTarefa() {
            System.out.println("Digite o título da tarefa que deseja atualizar a prioridade:");
            String titulo = scanner.next();
            System.out.println("Digite a nova prioridade da tarefa (1 - Baixa, 2 - Média, 3 - Alta):");
            int prioridade = scanner.nextInt();
            var novaPrioridade = switch (prioridade) {
                case 1 -> Prioridade.BAIXA;
                case 2 -> Prioridade.MEDIA;
                case 3 -> Prioridade.ALTA;
                default -> Prioridade.MEDIA; 
            };
            if (tarefas.atualizarPrioridadeTarefa(titulo, novaPrioridade)) {
                System.out.println(GREENP + "Prioridade atualizada com sucesso!" + RESETP);
            } else {
                System.out.println(REDP + "Tarefa não encontrada." + RESETP);
            }
        }
    }

   
    
     
