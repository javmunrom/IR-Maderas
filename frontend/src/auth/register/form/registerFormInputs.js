import { formValidators } from '../../../validators/formValidators'

export const registerFormInputs = [
  {
    tag: 'Email',
    name: 'email',
    type: 'text',
    defaultValue: '',
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: 'Username',
    name: 'username',
    type: 'text',
    defaultValue: '',
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },

  {
    tag: 'Password',
    name: 'password',
    type: 'password',
    defaultValue: '',
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: 'Phone',
    name: 'phone',
    type: 'text',
    defaultValue: '',
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
]
