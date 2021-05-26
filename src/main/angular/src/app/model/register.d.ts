import { Beneficial } from './beneficial';

export interface Register {
    name: string,
    address: string,
    regCode: string,
    sepa: string,
    registered: Date,
    closed: Date,
    regTypeText: string,
    beneficials: Beneficial[],
}