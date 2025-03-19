package edu.icet.crn.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private Long id;
    private String name;
    private String contactNumber;

}
