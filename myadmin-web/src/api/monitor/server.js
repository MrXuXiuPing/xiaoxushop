import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/server',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/server',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/server',
    method: 'put',
    data
  })
}

export default { add, edit, del }

export function count() {
  return request({
    url: 'api/visits',
    method: 'post'
  })
}

export function get() {
  return request({
    url: 'api/visits',
    method: 'get'
  })
}

export function getChartData() {
  return request({
    url: 'api/visits/chartData',
    method: 'get'
  })
}
