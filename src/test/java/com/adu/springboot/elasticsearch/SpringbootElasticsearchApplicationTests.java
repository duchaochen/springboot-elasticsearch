package com.adu.springboot.elasticsearch;

import com.adu.springboot.elasticsearch.bean.Book;
import com.adu.springboot.elasticsearch.bean.UserInfo;
import com.adu.springboot.elasticsearch.service.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootElasticsearchApplicationTests {

	@Autowired
	private JestClient jestClient;

	@Autowired
	private BookRepository bookRepository;

	@Test
	public void setTest01(){
		Book book = new Book(1, "西游记", "吴承恩");
		bookRepository.index(book);
	}


	@Test
	public void setTest(){

		UserInfo user = new UserInfo(1,"zhangsan1","123456");
		//构建一个索引为temp（库），类型（表）为userInfo,id为1的数据
		Index index = new Index.Builder(user).index("temp").type("userinfo").id("1").build();

		//执行索引
		try {
			jestClient.execute(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
