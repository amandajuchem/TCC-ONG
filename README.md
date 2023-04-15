# Projeto API

Este projeto foi construido utilizando Java 17 + Spring Boot 2.7.10 no backend e TypeScript + Angular 15

## Instalação do backend no Docker

Utilize o Maven para fazer o build do backend

Crie a imagem do projeto

<b>Obs.:</b> Substitua a palavra <b>versão</b> no comando a seguir pelo número da versão da compilação, por exemplo, por 1.0.0; 

<pre><code>docker build -t projeto-backend:versão* .</code></pre>

Crie a rede do projeto

<pre><code>docker network create projeto</code></pre>

Execute a imagem do projeto

<b>Obs.:</b> Substitua a palavra <b>versão</b> no comando a seguir pelo número da versão da imagem, por exemplo, por 1.0.0;  

<pre><code>docker run -p 8080:8080 --name projeto-backend --network projeto -d projeto-backend:versão</code></pre>

## Instalação do frontend no Docker

Crie a imagem do projeto

<b>Obs.:</b> Substitua a palavra <b>versão</b> no comando a seguir pelo número da versão da compilação, por exemplo, por 1.0.0; 

<pre><code>docker build -t projeto-frontend:versão .</code></pre>

Execute a imagem do projeto

<b>Obs.:</b> Substitua a palavra <b>versão</b> no comando a seguir pelo número da versão da imagem, por exemplo, por 1.0.0;  

<pre><code>docker run -p 80:80 --name projeto-frontend --network projeto -d projeto-frontend:versão</code></pre>