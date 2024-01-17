import instance from "@/util/axiosUtil";

const user_request={
     getUserDetailsById(id){
         return instance({
             method:'get',
             url:'/getUserDetailsById/'+id
         })
     }
}

export default user_request;
