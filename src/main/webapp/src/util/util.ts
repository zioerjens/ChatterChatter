export interface ID {
  id?: number;
}

export function mapById<T extends ID>(oldArr: T[], newArr: T[]) {
  newArr.forEach(newElement => {
    if (oldArr.find(oldElement => oldElement.id === newElement.id) === undefined) {
      oldArr.push(newElement);
    }
  })
  if (oldArr.length > newArr.length) {
    oldArr.forEach(oldElement => {
      if (newArr.find(newElement => newElement.id === oldElement.id) === undefined) {
        newArr.splice(oldArr.indexOf(oldElement), 1);
      }
    })
  }
}

export function isNotEmpty(obj: unknown) {
  return !isEmpty(obj);
}

export function isEmpty(obj: unknown) {
  return obj === undefined || obj === null;
}

export function requireNonNull<T>(obj: T) {
  if (isEmpty(obj)) {
    throw new Error('Object should not be null or undefined');
  }
  return obj!;
}
