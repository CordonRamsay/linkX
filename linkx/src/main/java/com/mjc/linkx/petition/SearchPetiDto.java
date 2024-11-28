package com.mjc.linkx.petition;

import com.mjc.linkx.common.dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SearchPetiDto extends SearchDto {
    private Integer total;
    private String searchField;                   //검색할때 참조할 청원 분야
    private String petiField;
    private Boolean playing;
    private String searchType;
    //검색할때 참조할 현재 청원이 종료됬는지 확인하는 불값

    private Integer page;

    private String orderByWord;//정렬할 컬럼
    private String sortColumn;
    private String sortAscDesc;
    private Integer rowsOnePage;
    private Integer firstIndex;
    public Integer getFirstIndex() {
        return (this.page-1) * this.rowsOnePage;
    }
    public void settingValues(){
        this.setOrderByWord((this.getSortColumn() != null ? this.getSortColumn():"id")+""+(this.getSortAscDesc() != null ? this.getSortAscDesc(): "DESC"));
        if(this.getSearchType() == null){
            this.setSearchType("title");
        }
        if(this.getRowsOnePage() == null){
            this.setRowsOnePage(10);
        }
        if(this.getPage() == null || this.getPage() <= 0){
            this.setPage(1);
        }
    }
}
