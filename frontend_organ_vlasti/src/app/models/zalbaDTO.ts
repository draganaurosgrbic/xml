import { Referenca } from './referenca';

export interface ZalbaDTO{
    tipZalbe: string;
    broj: number;
    datum: string;
    status: string;
    reference: Referenca[];
}

