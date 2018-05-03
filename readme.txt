# BankApplication
A remote Bank application using Remote Method Interface (RMI) with basic operations like: create account, deposit, withdraw, tranfer and print statement


Este projeto foi desenvolvido usando JRE 1.8, Eclipse Neon e SQLite 3.7.2. Para executá-la, siga os passos da seção seguinte.

# Instruções para execução do sistema

1. Clone esse repositório para a sua máquina.</br>
2. Abra o Eclipse Neon IDE.</br>
3. No Eclipse, vá em "File > Open Projects from File System... > Directory...".</br>
 3.1. Escolha a pasta que contém o projeto clonado.</br>
 3.2. tenha certeza de marcar as opções "Search for nested projects" e "Detect and configure project natures".</br>
4. Clique em "Finish".</br>
5. No Eclipse, clique com o botão direito sobre o nome do projeto e clique em "Properties". Na janela que abriu, clique em 
   "Java Build Path" e selecione a aba "Libraries". Verifique se o JAR "sqlite-jdbc-3.7.2" está listado, caso contrário cli
   que em "Add External JARs" e selecione o JAR contido na pasta "external" do projeto clonado.</br>
6. Dentro do pacote "app", selecione a classe "RMIServer.java" e execute-a.</br>
 6.1. Clique no botão "Pin Console" e abra um novo console.</br>
7. Selecione o novo console que você abriu.</br>
 7.1 Dentro do pacote "app", selecione a classe "Client.java" e execute-a.</br>
 
 
FIM!
