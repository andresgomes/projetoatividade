# 📌 Projeto Java com JSF, Hibernate e PostgreSQL

Este projeto é desenvolvido em **Java 1.8** utilizando **JSF 2.2** para a camada de apresentação, **Hibernate 5.4.3** para persistência de dados e **PostgreSQL 16** como banco de dados relacional.  
A aplicação é empacotada via **Maven** e executada em um servidor **Tomcat 9.0**.

---

## 🛠 Tecnologias Utilizadas

- **Java**: 1.8  
- **IDE**: Eclipse 2025-06 (4.36.0)  
- **Maven**: Gerenciamento de dependências e build  
- **JSF**: 2.2 (JavaServer Faces)  
- **Hibernate**: 5.4.3 (JPA)  
- **Banco de Dados**: PostgreSQL 16  
- **Servidor de Aplicação**: Tomcat 9.0  

---

## 📂 Estrutura Geral do Projeto

/src/main/java → Código-fonte Java (entidades, serviços, DAOs, etc.)
/src/main/resources → Arquivos de configuração (persistence.xml, hibernate.cfg.xml, etc.)
/src/main/webapp → Páginas JSF (xhtml)
/pom.xml → Configuração do Maven e dependências

## ⚙ Configuração do Banco de Dados

O projeto está configurado para utilizar **PostgreSQL 16**.  
Você deve criar previamente o banco de dados e ajustar as credenciais no arquivo de configuração (`persistence.xml`).

Criação do banco:

sql
CREATE DATABASE atividadeesig;
CREATE USER usuario_banco WITH ENCRYPTED PASSWORD 'senha_banco';
GRANT ALL PRIVILEGES ON DATABASE nome_do_banco TO usuario_banco;

OBS.: As informações usadas em "usuario_banco" e "senha_banco" serão usadas no arquivo 'persistence.xml'.

🚀 Orientações de Importação e Execução

1️⃣ Importar o Projeto no Eclipse
Abra o Eclipse.

Vá em File → Import → Maven → Existing Maven Projects.

Selecione o diretório onde está o projeto e finalize.

Aguarde o Maven baixar todas as dependências.

2️⃣ Configurar o Banco de Dados
Abra o arquivo persistence.xml em src/main/resources/META-INF/.

Edite as propriedades abaixo com os dados do seu PostgreSQL:

xml
Copiar
Editar
<property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/nome_do_banco"/>
<property name="javax.persistence.jdbc.user" value="usuario_banco"/>
<property name="javax.persistence.jdbc.password" value="senha_banco"/>
<property name="javax.persistence.jdbc.driver" value="org.postgresql.Driver"/>
Certifique-se que o PostgreSQL esteja rodando e o banco criado.

3️⃣ Criar as Tabelas via Hibernate
Localize a classe StartJPA.java no projeto.

Clique com o botão direito sobre o arquivo → Run As → Java Application.

O Hibernate criará automaticamente todas as tabelas mapeadas no banco.

4️⃣ Configurar o Tomcat no Eclipse
Baixe e extraia o Tomcat 9.0.

No Eclipse, vá em Window → Preferences → Server → Runtime Environments.

Clique em Add → Apache → Tomcat v9.0 e aponte para a pasta onde o Tomcat foi extraído.

Finalize a configuração.

5️⃣ Garantir que o Tomcat use as Libs do Maven
Para evitar precisar copiar manualmente os JARs para a pasta lib do Tomcat:

Clique com o botão direito no projeto → Properties.

Vá em Deployment Assembly.

Verifique se há uma entrada Maven Dependencies → /WEB-INF/lib.

Se não houver, clique em Add → Java Build Path Entries → Maven Dependencies.

Salve as alterações.
Assim, o Eclipse empacota automaticamente as dependências do Maven no WAR, e o Tomcat as reconhecerá sem precisar mexer na pasta lib dele.

6️⃣ Executar a Aplicação Web
Clique com o botão direito no projeto → Run As → Run on Server.

Escolha o Tomcat configurado e clique em Finish.

Acesse a aplicação no navegador:


http://localhost:8080/projetoatividade
