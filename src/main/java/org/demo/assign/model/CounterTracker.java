package org.demo.assign.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "COUNTER_TBL")
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class CounterTracker {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int counter;
  private long timeStamp;
}
