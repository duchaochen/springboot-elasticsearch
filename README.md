### es存储结构介绍
    1.索引对应的数据库
    2.类型对应的数据库的表
    3.文档对应的表中的整行数据
    4.属性对应的表中的字段
    
    

### jest操作es

    1.得注释掉
        spring-boot-starter-data-elasticsearch的jar
        
    2.引入jest的jar包
    
        <dependency>
            <groupId>io.searchbox</groupId>
            <artifactId>jest</artifactId>
            <version>5.3.3</version>
        </dependency>
    3.修改配置，由于默认es的uri配置为http://localhost:9200
        spring.elasticsearch.jest.uris=http://192.168.25.144:9200
        
        注意：修改成功之后运行看看是否报错，不抱错证明启用jest的jar成功
        
    4.添加一个新索引，注意索引名称最好不要使用特殊字符以及大写字符
        代码如下：
            	@Test
            	public void setTest(){
            
            		UserInfo user = new UserInfo(1,"zhangsan1","123456");
            		//构建一个索引为temp（库），类型（表）为userInfo,id为1的数据
            		Index index = new Index.Builder(user).index("temp").type("userinfo").id("1").build();
            		
            		try {
            		    //执行索引
            			jestClient.execute(index);
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
            	}
### spring-boot-starter-data-elasticsearch操作es

    注意：目前需要使用springboot1.5.12版本，对应es2.4.6版本，这个方式操作没有问题

    1.引入
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        </dependency>
        
     注意：一定引入的版本要和es安装版本一致
            spring-data-elasticsearch	elasticsearch
               3.1.x	                    6.2.2
               3.0.x	                    5.5.0
               2.1.x	                    2.4.0
               2.0.x	                    2.2.0
               1.3.x	                    1.5.2
          此版本用的使用spring-data-elasticsearch为：3.0.x
          elasticsearch安装包的版本的5，所以可以对应上。
    
    2.全局配置文件，需要加上以下代码
        #为访问的http://192.168.25.144:9200获取es的名称： "cluster_name" : "elasticsearch"的值。
        spring.data.cassandra.cluster-name=elasticsearch
        #这个是es的安装时的通讯端口为：9300
        spring.data.elasticsearch.cluster-nodes=192.168.25.144:9301
        
    测试代码：
        1.创建一个book类，加上文档注解，添加交换器名称，类型名称
        代码如下：
            
        @Document(indexName = "kindstart",type = "book")
        public class Book {
        
            private Integer id;
            private String bookName;
            private String author;
            
            ....
            ....
            ....
        }
        indexName：索引名称（理解为库名）
        type:类型名称（理解为表名）
        
        2.书写一个BookRepository接口，继承ElasticsearchRepository接口
            代码：
                public interface BookRepository extends ElasticsearchRepository<Book,Integer> {
                }
                
        3.新增代码：
            @Autowired
            private BookRepository bookRepository;
        
            @Test
            public void setTest01(){
                Book book = new Book(1, "西游记", "吴承恩");
                bookRepository.index(book);
            }
            
    链接方式：http://192.168.25.144:9201访问,通讯端口为http://192.168.25.144:9301