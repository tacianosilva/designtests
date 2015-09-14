#Script R para gerar os Dados agragados

results = read.csv('/home/taciano/dev/workspace/designtests/scripts/results_2015-08-20.txt')
results_star = read.csv('/home/taciano/dev/workspace/designtests/scripts/results_star_2015-09-01.txt')

results_rules = aggregate(results$rule, list(resultado = results$result, regra = results$rule), length)
results_rules_total = aggregate(results$rule, list(resultado = results$rule), length)
colnames(results_rules_total) <- c("regra", "total")
results_rules.falhou <- results_rules[which(results_rules$resultado == 'false'),]
results_rules.falhou["resultado"] <- NULL
colnames(results_rules.falhou) <- c("regra", "falhou")
results_rules_falhou = merge(results_rules_total, results_rules.falhou, all.x=TRUE, incomparables = NULL)
results_rules_falhou[is.na(results_rules_falhou)] <- 0
results_rules_falhou["proporcao"] <- results_rules_falhou$falhou / results_rules_falhou$total

results_star_rules = aggregate(results_star$rule, list(resultado = results_star$result, regra = results_star$rule), length)
results_star_rules_total = aggregate(results_star$rule, list(resultado = results_star$rule), length)
colnames(results_star_rules_total) <- c("regra", "total")
results_star_rules.falhou <- results_star_rules[which(results_star_rules$resultado == 'false'),]
results_star_rules.falhou["resultado"] <- NULL
colnames(results_star_rules.falhou) <- c("regra", "falhou")
results_star_rules_falhou = merge(results_star_rules_total, results_star_rules.falhou, all.x=TRUE, incomparables = NULL)
results_star_rules_falhou[is.na(results_star_rules_falhou)] <- 0
results_star_rules_falhou["proporcao"] <- results_star_rules_falhou$falhou / results_star_rules_falhou$total

results_projects = aggregate(results$project, list(resultado = results$result, projeto = results$project), length)
results_star_projects = aggregate(results_star$project, list(resultado = results_star$result, projeto = results_star$project), length)

results_total = aggregate(results$project, list(resultado = results$project), length)
colnames(results_total) <- c("projeto", "total")
results_star_total = aggregate(results_star$project, list(resultado = results_star$project), length)
colnames(results_star_total) <- c("projeto", "total")

results_projects.passou <- results_projects[which(results_projects$resultado == 'true'),]
results_projects.falhou <- results_projects[which(results_projects$resultado == 'false'),]

results_star_projects.passou <- results_star_projects[which(results_star_projects$resultado == 'true'),]
results_star_projects.falhou <- results_star_projects[which(results_star_projects$resultado == 'false'),]

results_projects.falhou["resultado"] <- NULL
colnames(results_projects.falhou) <- c("projeto", "falhou")
results_proj_falhou = merge(results_total, results_projects.falhou, all.x=TRUE, incomparables = NULL)
results_proj_falhou[is.na(results_proj_falhou)] <- 0
results_proj_falhou["proporcao"] <- results_proj_falhou$falhou / results_proj_falhou$total

results_star_projects.falhou["resultado"] <- NULL
colnames(results_star_projects.falhou) <- c("projeto", "falhou")
results_star_proj_falhou = merge(results_star_total, results_star_projects.falhou, all.x=TRUE, incomparables = NULL)
results_star_proj_falhou[is.na(results_star_proj_falhou)] <- 0
results_star_proj_falhou["proporcao"] <- results_star_proj_falhou$falhou / results_star_proj_falhou$total

write.csv(results_proj_falhou, file = "/home/taciano/dev/workspace/designtests/scripts/results_proportions.txt", row.names=FALSE)
write.csv(results_star_proj_falhou, file = "/home/taciano/dev/workspace/designtests/scripts/results_star_proportions.txt", row.names=FALSE)
write.csv(results_rules_falhou, file = "/home/taciano/dev/workspace/designtests/scripts/results_rules_proportions.txt", row.names=FALSE)
write.csv(results_star_rules_falhou, file = "/home/taciano/dev/workspace/designtests/scripts/results_star_rules_proportions.txt", row.names=FALSE)
