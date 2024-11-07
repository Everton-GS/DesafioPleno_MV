# Desafio de Backend Java Pleno com Spring Boot: Sistema de Notificações

## 📚Informação do Sistema👨‍🏫👩‍🎓
O sistema em questão é um gerenciador de notificações onde o usuário logado na aplicação, consegue enviar mensagens para outro usuário também registrado.

<h3>Funcionalidade do Usuário<br> </h3>   
 
+ Registrar notificação.<br>
+ Atualizar notificação como lida.<br>
+ Listar notificação.<br>

## Implementações adicionais
<h3>Funcionalidade do Administrador<br></h3>

+ Registrar Usuário.<br>
+ Agendar envio de e-mail.<br>
+ Enviar e-mail.<br>

No tocante a funcionalidade de agendamento de envio de e-mail, foi utilizando para a sua implementação o JavaMailSender. Partindo da premissa de que é de grande relevância contactar os usuários de forma específica, seja com comunicados de manutenção, informações de atualizações ou novas implementações. 
Ainda referente a funcionadade de envio de e-mail no que se trata da automatização dos agendamentos, foi utilizado o ferramenta scheduled job que instância um serviço a cada minuto para consultar o banco de dados, validando as mensagens com status "agendada" e verificando se o horário de envio já passou, para então enviá-las.

## Arquitetura Software💻
O projeto foi desenvolvido utilizando o padrão API RESTful, que segue os princípios da arquitetura REST (Representational State Transfer). A aplicação foi estruturada para permitir a comunicação eficiente entre o cliente e o servidor, utilizando métodos HTTP como GET, POST, PUT e DELETE para gerenciar recursos. 

## Segurança 🔐
O sistema de segurança utilizando na implementação foi o Spring Security para facilitar a autenticação e autorização. O método de autenticação escolhido foi o JSON Web Token (JWT), e todas as rotas, exceto as de acesso público, devem exigir esse método de autenticação. Além disso, foi utilizado o BCrypt para criptografar as senhas dos usuários, garantindo que, mesmo que o banco de dados seja violado, as credenciais de acesso ainda estarão seguras.

## Conteinerização da aplicação 🐳
O sistema foi conteinizado utilizando o Docker com o objetivo de evitar conflitos e tornar o ambiente mais intuitivo para outros desenvolvedores. 

<h3>Sistemas dentro do container</h3>

## PhpMyadmin
O phpMyAdmin é uma ferramenta de gerenciamento de banco de dados que permite visualizar e manipular dados armazenados de forma prática e intuitiva.
```
Rota de acesso:http://localhost:8000/

Crendenciais de Acesso

usuario:root

senha:java
```
![phpMyAdmin](./imagens/minha-imagem.pngphpMyAdmin.png)
![Descrição da Imagem](./img/phpMyAdmin.png)

Esta imagem mostra o sistema interno do SGBD (Sistema de Gerenciamento de Banco de Dados).

![sistema sgbd](https://github.com/user-attachments/assets/2b1470ba-2656-4a40-930b-1ae0622a1f50)

## Mysql
Configurações do Mysql

```
versao:mysql:8.0.31

porta:3306:3306

MYSQL_DATABASE: db_mv

MYSQL_ROOT_PASSWORD: java
```

## Prometheus
O Prometheus é utilizado para capturar, armazenar e consultar métricas do sistema em tempo real.

```
Rota de acesso:http://localhost:9090
```
Imagem da interface do sistema interno do Prometheus

![prometheus](https://github.com/user-attachments/assets/7a5e3394-3d46-4e20-bf15-da9269a7cf8e)

A consulta de métricas é realizada clicando no ícone do globo

![selecaoM](https://github.com/user-attachments/assets/56cbfd6f-fccf-4b49-914c-d54f4ec516af)

É apresentada uma lista de métricas que podem ser executadas para obter os resultados desejados. Escolha uma métrica e, em seguida, execute-a para visualizar as métricas registradas

![seleExecute](https://github.com/user-attachments/assets/dc64102a-00f7-48b4-b17f-74ea82790bff)

O resultado da consulta é apresentado dessa maneira.

![resultado](https://github.com/user-attachments/assets/7e9bb53b-4b91-4a73-aeae-649b0c1ba483)

## Grafana

O Grafana utiliza as métricas coletadas pelo Prometheus e as exibe em um dashboard intuitivo e visualmente atraente, facilitando a análise e o monitoramento dos dados.

```
Rota de acesso:: http://localhost:3000/login

Crendenciais de Acesso

usuario:admin

senha:testePlenoMV
```
![grafana](https://github.com/user-attachments/assets/d65156aa-56bf-4cea-aa83-905136a3cf66)

Clicando na opção menu "Dashboard", exibirá um modelo de apresentação ao qual será aplicado.

![abridash](https://github.com/user-attachments/assets/3cd6a594-c8c3-427c-8769-649fa2656300)

Clicando no modelo em questão: "spring boot statistics & endpoint metrics".

![Selecione](https://github.com/user-attachments/assets/f236daa9-e87f-4f0f-9819-c6341b6b2d0c)

O dashboard será apresentado desta maneira:

![dadosM](https://github.com/user-attachments/assets/9355b550-9fc3-4e92-b540-c29d2a7d8e75)

## Inicialização aplicação

```
 docker-compose up --build -d
```

## Informações de Acesso
O acesso é realizado usando e-mail e senha. Quando a aplicação é inicializada, um usuário administrador é registrado automaticamente, com permissões para registrar outros usuários.

Vale salientar que a inicialização do usuário administrador é totalmente segura, pois ele é registrado com uma senha criptografada, como é demostrando na imagem abaixo.

![inicialziarAdmin](https://github.com/user-attachments/assets/b8258443-d60e-4a7a-9a60-9d16eeb7fb9d)

```
crendenciais de acesso

Email:AdministradorMV01@gmail.com

Senha:testePlenoMV
```
## Documentação da API com Swagger

O sistema utiliza o Swagger para documentar todas as rotas da API, fornecendo uma interface de usabilidade intuitiva. Essa interface exibe automaticamente um JSON predefinido para cada rota, facilitando a interação e o teste das APIs diretamente na área compartilhada por eles.
```
Rota de acesso: http://localhost:8080/swagger-ui/index.html
```
Essa é a interface gráfica do Swagger

![Swagger](https://github.com/user-attachments/assets/ab136c20-1239-43b5-b842-53553d5f684c)

O sistema requer um token para acessar as rotas, por isso é necessário fazer o login na aplicação para obter o token que será utilizado nas rotas que exigem autenticação.

Siga o passo a passo demostrado para realizar o login.

![acessarAplicacao](https://github.com/user-attachments/assets/6a10e3e7-35e5-4ccb-8abd-672afc934f5f)

Clique na opção "Try in out" para implementar os dados que serão utilizados na requisição de login.

![acessarTy](https://github.com/user-attachments/assets/173ca4fb-7fc9-47c1-86b3-3cf5c84cb8d9)

Implemente os dados conforme demonstrado na imagem abaixo e execute a requisição.

![Implemente](https://github.com/user-attachments/assets/693b3d06-bffa-4e24-a4db-8ba1756f77cd)

Copie o token recebido na imagem abaixo e utilize-o na aplicação.

![token](https://github.com/user-attachments/assets/4ffd1e1d-d570-47e8-b822-4bf0f8bdb559)

O token deve ser registrado no Swagger para todas as requisições, conforme demonstrado na imagem.

![Authorize](https://github.com/user-attachments/assets/a3d09aea-a768-4a9f-a077-5f7d0e213352)

Implemente o token no campo indicado e clique em "Authorize".

![implementado](https://github.com/user-attachments/assets/1cd77052-2cfe-4ed6-8d80-a1bc44f63835)

Após implementar o token, feche a janela clicando no "X", conforme demonstrado na imagem.

![fechar](https://github.com/user-attachments/assets/33399524-a253-4581-8488-57a6e19ce7e0)

Com a conclusão desse passo a passo, todas as rotas que necessitam de token funcionarão corretamente.Como comprovado na imagem. 

![registrar](https://github.com/user-attachments/assets/e0458107-f532-4930-97a1-5182d2a9e7d2)
