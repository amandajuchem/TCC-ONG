import { Porte } from '../enums/porte';
import { Sexo } from '../enums/sexo';
import { Situacao } from '../enums/situacao';
import { AbstractEntity } from './abstract-entity';
import { Adocao } from './adocao';
import { FichaMedica } from './ficha-medica';

export interface Animal extends AbstractEntity {
    nome: string;
    idade: number;
    especie: string;
    sexo: Sexo;
    raca: string;
    cor: string;
    porte: Porte;
    situacao: Situacao;
    foto: any;
    fichaMedica: FichaMedica;
    adocoes: Array<Adocao>;
}