package project.blobus.Backend.member.basic.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> {
    private List<E> dtoList;
    private List<Integer> pageNumList;
    private PageRequestDTO pageRequestDTO;
    private boolean prev, next;
    private int totalCount, pageSize, prevPage, nextPage, currentPage;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) {
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int) totalCount;

        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;
        int start = end - 9;
        int last = (int) (Math.ceil(totalCount / (double) pageRequestDTO.getSize()));
        if (end > last) end = last;

        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        this.pageSize = this.pageNumList.size();

        this.prev = start > 1;
        this.next = totalCount > (long) end * pageRequestDTO.getSize();

        if (prev) this.prevPage = start - 1;
        if (next) this.nextPage = end + 1;
        this.currentPage = pageRequestDTO.getPage();
    }
}
