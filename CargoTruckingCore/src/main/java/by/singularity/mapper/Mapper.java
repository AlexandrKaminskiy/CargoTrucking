package by.singularity.mapper;

public interface Mapper<Model,Dto> {
    Model toModel(Dto dto);
    Dto toDto(Model model);
}
