import { AbstractEntity } from "./abstract-entity";

export interface Exame extends AbstractEntity {
    nome: string;
    categoria: string;
}