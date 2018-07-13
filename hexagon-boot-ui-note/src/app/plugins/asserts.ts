
export function assertNotNull(v, msg) {
  if(v===null) {
    throw Error(msg)
  }
}

export function assertNotBlank(v, msg) {
  if(!v || v.trim()==='') {
    throw Error(msg)
  }
}
