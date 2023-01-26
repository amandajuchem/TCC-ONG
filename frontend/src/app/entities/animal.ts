import { Porte } from '../enums/porte';
import { Sexo } from '../enums/sexo';
import { Situacao } from '../enums/situacao';
import { AbstractEntity } from './abstract-entity';
import { FichaMedica } from './ficha-medica';
import { Tutor } from './tutor';

export interface Animal extends AbstractEntity {
    nome: string;
    idade: number;
    especie: string;
    local: string;
    localAdocao: string;
    sexo: Sexo;
    raca: string;
    dataAdocao: Date;
    dataResgate: Date;
    cor: string;
    porte: Porte;
    castrado: boolean;
    situacao: Situacao;
    tutor: Tutor;
    foto: any;
    fichaMedica: FichaMedica;
}