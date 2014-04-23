namespace cpp org.hy.dev.rpc.thrift.shared
namespace d org.hy.dev.rpc.thrift.share
namespace java org.hy.dev.rpc.thrift.shared
namespace perl org.hy.dev.rpc.thrift.shared
namespace php org.hy.dev.rpc.thrift.shared

struct SharedStruct{
	1: i32 key
	2: string value
}

service SharedService{
	SharedStruct getStruct(1: i32 key)
}