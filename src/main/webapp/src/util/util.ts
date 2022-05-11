export interface ID {
  id?: number;
}

export function mapById<T extends ID>(oldArr: T[], newArr: T[], eventIfMapped?: () => void) {
  let mapped = false;
  newArr.forEach(newElement => {
    if (oldArr.find(oldElement => oldElement.id === newElement.id) === undefined) {
      oldArr.push(newElement);
      mapped = true;
    }
  })
  if (oldArr.length > newArr.length) {
    oldArr.forEach(oldElement => {
      if (newArr.find(newElement => newElement.id === oldElement.id) === undefined) {
        oldArr.splice(oldArr.indexOf(oldElement), 1);
        mapped = true;
      }
    })
  }
  if (mapped && isNotEmpty(eventIfMapped)) {
    eventIfMapped!();
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

export function dateToTime(timeObject: Date): string {
  const hours = timeObject.getHours();
  const minutes = timeObject.getMinutes();
  const minuteString = minutes < 10 ? '0' + minutes : minutes;
  const hoursString = hours < 10 ? '0' + hours : hours;
  return hoursString + ':' + minuteString;
}
