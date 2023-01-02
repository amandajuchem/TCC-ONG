import { Estado } from "../enums/estado";
import { AbstractEntity } from "./abstract-entity";

export interface Endereco extends AbstractEntity {
    rua: string;
    numeroResidencia: string;
    bairro: string;
    cidade: string;
    estado: Estado;
    complemento: string;
    cep: string;
}