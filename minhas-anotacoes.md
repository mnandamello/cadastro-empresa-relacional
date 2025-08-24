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
    5.	Monitoramento e Logs:
          •	Configurar logs com SLF4J e Logback.
          •	Implementar endpoints de saúde com Spring Boot Actuator.
    6.	Implantação:
          •	Fornecer instruções para rodar localmente com Docker.
          •	Criar arquivos Dockerfile e docker-compose.yml para facilitar a execução.


# Comando para mexer no banco:
    - docker exec -it nome_do_container mysql -u root -p
    - show databases;
    - use <database_name>;
    - show tables;
    - DROP TABLE IF EXISTS addresses;