import { formValidators } from '../../../validators/formValidators'

export const nuevaPiezaFormInputs = [
  {
    tag: 'Canto Lado Largo',
    name: 'cantoLadoLargo',
    type: 'number',
    defaultValue: '',
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: 'Canto Lado Corto',
    name: 'cantoLadoCorto',
    type: 'number',
    defaultValue: '',
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: 'Medida Largo',
    name: 'medidaLargo',
    type: 'number',
    defaultValue: '',
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: 'Medida Corto',
    name: 'medidaCorto',
    type: 'number',
    defaultValue: '',
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: 'Diseño',
    name: 'diseño',
    type: 'text',
    defaultValue: '',
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: 'Cantidad',
    name: 'cantidad',
    type: 'number',
    defaultValue: 1,
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
]
