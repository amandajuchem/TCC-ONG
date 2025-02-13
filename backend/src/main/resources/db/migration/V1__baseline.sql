CREATE TABLE tb_adocoes
(
    id                 UUID                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    data_hora          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    local              VARCHAR(10),
    local_adocao       VARCHAR(10),
    vale_castracao     BOOLEAN                     NOT NULL,
    animal_id          UUID,
    tutor_id           UUID,
    feira_adocao_id    UUID,
    CONSTRAINT pk_tb_adocoes PRIMARY KEY (id)
);

CREATE TABLE tb_adocoes_termo_responsabilidade
(
    tb_adocoes_id             UUID NOT NULL,
    termo_responsabilidade_id UUID NOT NULL
);

CREATE TABLE tb_agendamentos
(
    id                 UUID                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    data_hora          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    descricao          VARCHAR,
    animal_id          UUID,
    veterinario_id     UUID,
    CONSTRAINT pk_tb_agendamentos PRIMARY KEY (id)
);

CREATE TABLE tb_animais
(
    id                 UUID        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(50),
    idade              INTEGER     NOT NULL,
    especie            VARCHAR(10) NOT NULL,
    sexo               VARCHAR(5)  NOT NULL,
    raca               VARCHAR(50),
    cor                VARCHAR(50),
    porte              VARCHAR(10),
    situacao           VARCHAR(10) NOT NULL,
    foto_id            UUID,
    ficha_medica_id    UUID,
    CONSTRAINT pk_tb_animais PRIMARY KEY (id)
);

CREATE TABLE tb_arquivos
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(25),
    CONSTRAINT pk_tb_arquivos PRIMARY KEY (id)
);

CREATE TABLE tb_atendimentos
(
    id                 UUID                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    data_hora          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    motivo             VARCHAR(25)                 NOT NULL,
    diagnostico        VARCHAR,
    posologia          VARCHAR,
    animal_id          UUID,
    veterinario_id     UUID,
    CONSTRAINT pk_tb_atendimentos PRIMARY KEY (id)
);

CREATE TABLE tb_atendimentos_exames_realizados
(
    exames_realizados_id UUID NOT NULL,
    tb_atendimentos_id   UUID NOT NULL
);

CREATE TABLE tb_empresas
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(100),
    cnpj               VARCHAR(14),
    endereco_id        UUID,
    CONSTRAINT pk_tb_empresas PRIMARY KEY (id)
);

CREATE TABLE tb_empresas_telefones
(
    tb_empresas_id UUID NOT NULL,
    telefones_id   UUID NOT NULL
);

CREATE TABLE tb_enderecos
(
    id                 UUID        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    rua                VARCHAR(100),
    numero_residencia  VARCHAR(10) NOT NULL,
    bairro             VARCHAR(50),
    cidade             VARCHAR(100),
    estado             VARCHAR(25) NOT NULL,
    complemento        VARCHAR(100),
    cep                VARCHAR(8),
    CONSTRAINT pk_tb_enderecos PRIMARY KEY (id)
);

CREATE TABLE tb_exames
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(100),
    categoria          VARCHAR(25),
    CONSTRAINT pk_tb_exames PRIMARY KEY (id)
);

CREATE TABLE tb_exames_realizados
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    exame_id           UUID,
    arquivo_id         UUID,
    CONSTRAINT pk_tb_exames_realizados PRIMARY KEY (id)
);

CREATE TABLE tb_feiras_adocao
(
    id                 UUID                        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(100),
    data_hora          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_tb_feiras_adocao PRIMARY KEY (id)
);

CREATE TABLE tb_feiras_adocao_animais
(
    animais_id          UUID NOT NULL,
    tb_feiras_adocao_id UUID NOT NULL,
    CONSTRAINT pk_tb_feiras_adocao_animais PRIMARY KEY (animais_id, tb_feiras_adocao_id)
);

CREATE TABLE tb_feiras_adocao_usuarios
(
    tb_feiras_adocao_id UUID NOT NULL,
    usuarios_id         UUID NOT NULL,
    CONSTRAINT pk_tb_feiras_adocao_usuarios PRIMARY KEY (tb_feiras_adocao_id, usuarios_id)
);

CREATE TABLE tb_fichas_medicas
(
    id                 UUID    NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    comorbidades       VARCHAR,
    castrado           BOOLEAN NOT NULL,
    CONSTRAINT pk_tb_fichas_medicas PRIMARY KEY (id)
);

CREATE TABLE tb_observacoes
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    conteudo           VARCHAR,
    tutor_id           UUID,
    CONSTRAINT pk_tb_observacoes PRIMARY KEY (id)
);

CREATE TABLE tb_telefones
(
    id                 UUID NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    numero             VARCHAR(11),
    CONSTRAINT pk_tb_telefones PRIMARY KEY (id)
);

CREATE TABLE tb_tutores
(
    id                 UUID        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(100),
    cpf                VARCHAR(11),
    rg                 VARCHAR(13),
    situacao           VARCHAR(10) NOT NULL,
    endereco_id        UUID,
    CONSTRAINT pk_tb_tutores PRIMARY KEY (id)
);

CREATE TABLE tb_tutores_telefones
(
    tb_tutores_id UUID NOT NULL,
    telefones_id  UUID NOT NULL
);

CREATE TABLE tb_usuarios
(
    id                 UUID        NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    created_by_user    VARCHAR(11),
    modified_by_user   VARCHAR(11),
    nome               VARCHAR(100),
    cpf                VARCHAR(11),
    senha              VARCHAR(255),
    status             BOOLEAN     NOT NULL,
    setor              VARCHAR(20) NOT NULL,
    CONSTRAINT pk_tb_usuarios PRIMARY KEY (id)
);

ALTER TABLE tb_adocoes_termo_responsabilidade
    ADD CONSTRAINT uc_tb_adocoes_termo_responsabilidade_termoresponsabilidade UNIQUE (termo_responsabilidade_id);

ALTER TABLE tb_arquivos
    ADD CONSTRAINT uc_tb_arquivos_nome UNIQUE (nome);

ALTER TABLE tb_atendimentos_exames_realizados
    ADD CONSTRAINT uc_tb_atendimentos_exames_realizados_examesrealizados UNIQUE (exames_realizados_id);

ALTER TABLE tb_empresas
    ADD CONSTRAINT uc_tb_empresas_cnpj UNIQUE (cnpj);

ALTER TABLE tb_empresas_telefones
    ADD CONSTRAINT uc_tb_empresas_telefones_telefones UNIQUE (telefones_id);

ALTER TABLE tb_exames
    ADD CONSTRAINT uc_tb_exames_nome UNIQUE (nome);

ALTER TABLE tb_feiras_adocao
    ADD CONSTRAINT uc_tb_feiras_adocao_nome UNIQUE (nome);

ALTER TABLE tb_tutores
    ADD CONSTRAINT uc_tb_tutores_cpf UNIQUE (cpf);

ALTER TABLE tb_tutores_telefones
    ADD CONSTRAINT uc_tb_tutores_telefones_telefones UNIQUE (telefones_id);

ALTER TABLE tb_usuarios
    ADD CONSTRAINT uc_tb_usuarios_cpf UNIQUE (cpf);

ALTER TABLE tb_usuarios
    ADD CONSTRAINT uc_tb_usuarios_nome UNIQUE (nome);

ALTER TABLE tb_adocoes
    ADD CONSTRAINT FK_TB_ADOCOES_ON_ANIMAL FOREIGN KEY (animal_id) REFERENCES tb_animais (id);

ALTER TABLE tb_adocoes
    ADD CONSTRAINT FK_TB_ADOCOES_ON_FEIRAADOCAO FOREIGN KEY (feira_adocao_id) REFERENCES tb_feiras_adocao (id);

ALTER TABLE tb_adocoes
    ADD CONSTRAINT FK_TB_ADOCOES_ON_TUTOR FOREIGN KEY (tutor_id) REFERENCES tb_tutores (id);

ALTER TABLE tb_agendamentos
    ADD CONSTRAINT FK_TB_AGENDAMENTOS_ON_ANIMAL FOREIGN KEY (animal_id) REFERENCES tb_animais (id);

ALTER TABLE tb_agendamentos
    ADD CONSTRAINT FK_TB_AGENDAMENTOS_ON_VETERINARIO FOREIGN KEY (veterinario_id) REFERENCES tb_usuarios (id);

ALTER TABLE tb_animais
    ADD CONSTRAINT FK_TB_ANIMAIS_ON_FICHAMEDICA FOREIGN KEY (ficha_medica_id) REFERENCES tb_fichas_medicas (id);

ALTER TABLE tb_animais
    ADD CONSTRAINT FK_TB_ANIMAIS_ON_FOTO FOREIGN KEY (foto_id) REFERENCES tb_arquivos (id);

ALTER TABLE tb_atendimentos
    ADD CONSTRAINT FK_TB_ATENDIMENTOS_ON_ANIMAL FOREIGN KEY (animal_id) REFERENCES tb_animais (id);

ALTER TABLE tb_atendimentos
    ADD CONSTRAINT FK_TB_ATENDIMENTOS_ON_VETERINARIO FOREIGN KEY (veterinario_id) REFERENCES tb_usuarios (id);

ALTER TABLE tb_empresas
    ADD CONSTRAINT FK_TB_EMPRESAS_ON_ENDERECO FOREIGN KEY (endereco_id) REFERENCES tb_enderecos (id);

ALTER TABLE tb_exames_realizados
    ADD CONSTRAINT FK_TB_EXAMES_REALIZADOS_ON_ARQUIVO FOREIGN KEY (arquivo_id) REFERENCES tb_arquivos (id);

ALTER TABLE tb_exames_realizados
    ADD CONSTRAINT FK_TB_EXAMES_REALIZADOS_ON_EXAME FOREIGN KEY (exame_id) REFERENCES tb_exames (id);

ALTER TABLE tb_observacoes
    ADD CONSTRAINT FK_TB_OBSERVACOES_ON_TUTOR FOREIGN KEY (tutor_id) REFERENCES tb_tutores (id);

ALTER TABLE tb_tutores
    ADD CONSTRAINT FK_TB_TUTORES_ON_ENDERECO FOREIGN KEY (endereco_id) REFERENCES tb_enderecos (id);

ALTER TABLE tb_adocoes_termo_responsabilidade
    ADD CONSTRAINT fk_tbadoterres_on_adocao FOREIGN KEY (tb_adocoes_id) REFERENCES tb_adocoes (id);

ALTER TABLE tb_adocoes_termo_responsabilidade
    ADD CONSTRAINT fk_tbadoterres_on_arquivo FOREIGN KEY (termo_responsabilidade_id) REFERENCES tb_arquivos (id);

ALTER TABLE tb_atendimentos_exames_realizados
    ADD CONSTRAINT fk_tbateexarea_on_atendimento FOREIGN KEY (tb_atendimentos_id) REFERENCES tb_atendimentos (id);

ALTER TABLE tb_atendimentos_exames_realizados
    ADD CONSTRAINT fk_tbateexarea_on_exame_realizado FOREIGN KEY (exames_realizados_id) REFERENCES tb_exames_realizados (id);

ALTER TABLE tb_empresas_telefones
    ADD CONSTRAINT fk_tbemptel_on_empresa FOREIGN KEY (tb_empresas_id) REFERENCES tb_empresas (id);

ALTER TABLE tb_empresas_telefones
    ADD CONSTRAINT fk_tbemptel_on_telefone FOREIGN KEY (telefones_id) REFERENCES tb_telefones (id);

ALTER TABLE tb_feiras_adocao_animais
    ADD CONSTRAINT fk_tbfeiadoani_on_animal FOREIGN KEY (animais_id) REFERENCES tb_animais (id);

ALTER TABLE tb_feiras_adocao_animais
    ADD CONSTRAINT fk_tbfeiadoani_on_feira_adocao FOREIGN KEY (tb_feiras_adocao_id) REFERENCES tb_feiras_adocao (id);

ALTER TABLE tb_feiras_adocao_usuarios
    ADD CONSTRAINT fk_tbfeiadousu_on_feira_adocao FOREIGN KEY (tb_feiras_adocao_id) REFERENCES tb_feiras_adocao (id);

ALTER TABLE tb_feiras_adocao_usuarios
    ADD CONSTRAINT fk_tbfeiadousu_on_usuario FOREIGN KEY (usuarios_id) REFERENCES tb_usuarios (id);

ALTER TABLE tb_tutores_telefones
    ADD CONSTRAINT fk_tbtuttel_on_telefone FOREIGN KEY (telefones_id) REFERENCES tb_telefones (id);

ALTER TABLE tb_tutores_telefones
    ADD CONSTRAINT fk_tbtuttel_on_tutor FOREIGN KEY (tb_tutores_id) REFERENCES tb_tutores (id);