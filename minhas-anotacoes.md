# Annotations:
    - @Data -> A annotation data é muito utilizada quando se quer deixar o codigo menor e escrever menos, pq quando é usada ela implementa varias outras annotations,
        como getters e setter, RequiredArgsConstructor que gera um construtor com os campos para dados final, gera um construtor basico e toString, então isso te 
        poupa mt tempo pra escrever uma classe, um dto ou algo do tipo, mas é recomendado tomar cuidado para não deixar seu codigo totalmente aclopado ao framework
    - @AllArgsConstructor ->
    - @NoArgsConstructor ->
    - @RequiredArgsConstructor ->
    - @Entity ->
    - @GeneratedValue -> 
    - @Column
    - @Service ->
    - @Repository ->
    - @Service ->
    - @RestControllerAdvice -> 
    - @ExceptionHandler ->
    - @Autowired ->
    - @Override -> 
    - @Component ->
    - @Bean -> 


# Assuntos novos:
    - Exception Handller Pattern
    - Oauth2
    - Auth com jwt (filtros)
    - throws exceptions na anotação do metodo
    - Quando dar um return ou um throw new...


# Tarefas a serem feitas:
    5.  Adiconar try exception
    6.	Monitoramento e Logs:
          •	Configurar logs com SLF4J e Logback. (se der colocar nivel de logs)
          •	Implementar endpoints de saúde com Spring Boot Actuator.
    7.	Implantação:
          •	Fornecer instruções para rodar localmente com Docker.
          •	Criar arquivos Dockerfile e docker-compose.yml para facilitar a execução
    8.  Documentação:
          • Colocar swagger como doc
    9.  Passo-a-passo para rodar o projeto no docker


# Comando para mexer no banco:
    - docker exec -it nome_do_container mysql -u root -p
    - show databases;
    - use <database_name>;
    - show tables;
    - DROP TABLE IF EXISTS addresses;


# Refatorações a seres feitas:
    - Colocar as validações do service dentro de uma annotation personalizada
    - tirar as annotations da camada de domain e fazer td na mão
    - criar interfaces do service pra quando formos chamar um metodo não chamarmos a implementação e sim a interface
    - mudar como faço os metodos de create para usar o builder():
                Feedback feedback = Feedback.builder()
                .comentario(request.getComentario())
                .data(LocalDateTime.now())
                .materia(materia)
                .professor(professor)
                .aluno(aluno)
                .build();