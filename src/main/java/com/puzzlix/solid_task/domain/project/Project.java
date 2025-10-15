package com.puzzlix.solid_task.domain.project;

import com.puzzlix.solid_task.domain.issue.Issue;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"issues"})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    // 무조건 양방향 설정을 할 필요는 없다.
    // 나는 fk 주인이 아니야
//    @OneToMany(mappedBy = "project")
//    private List<Issue> issues = new ArrayList<>();
    // 이런 부분은 직접 쿼리를 구현하는 것이 좋다.


}
