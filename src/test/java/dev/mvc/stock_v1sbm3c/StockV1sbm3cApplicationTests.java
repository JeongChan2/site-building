package dev.mvc.stock_v1sbm3c;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mvc.category.CategoryDAOInter;
import dev.mvc.category.CategoryVO;

@SpringBootTest
class StockV1sbm3cApplicationTests {
  @Autowired // 객체를 만들어 자동으로 할당해라
  private CategoryDAOInter cateDAO;
	@Test
	void contextLoads() {
	}
	
	@Test
  public void testCreate() {
    CategoryVO cateVO = new CategoryVO();
//    cateVO.setName("경상남도");
    cateVO.setCate_name("전라북도");
    int cnt = this.cateDAO.create(cateVO);
    System.out.println("-> cnt: " + cnt);
  }

}
