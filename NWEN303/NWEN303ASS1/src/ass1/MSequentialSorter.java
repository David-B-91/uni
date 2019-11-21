package ass1;

import java.util.ArrayList;
import java.util.List;

public class MSequentialSorter implements Sorter {

  @Override
  public <T extends Comparable<? super T>> List<T> sort(List<T> list) {
    List<T>result=new ArrayList<>();
    for(T l:list){insert(result,l);}
    return result;
  }
  <T extends Comparable<? super T>> void insert(List<T> list, T elem){
    for(int i=0;i<list.size();i++){
      if(list.get(i).compareTo(elem)<0){continue;}
      list.add(i,elem);
      return;
    }
    list.add(elem);
  }
}
