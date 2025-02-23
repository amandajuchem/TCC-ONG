import { AbstractEntity } from "./abstract-entity";
import { Endereco } from "./endereco";
import { Telefone } from "./telefone";

export interface Empresa extends AbstractEntity {
    nome: string;
    cnpj: string;
    telefones: Array<Telefone>;
    endereco: Endereco;
}