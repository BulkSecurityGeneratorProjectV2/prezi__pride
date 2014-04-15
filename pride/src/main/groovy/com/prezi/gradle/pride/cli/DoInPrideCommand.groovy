package com.prezi.gradle.pride.cli

import com.prezi.gradle.pride.Pride
import io.airlift.command.Arguments
import io.airlift.command.Command
import io.airlift.command.Option
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by lptr on 10/04/14.
 */
@Command(name = "do", description = "Execute a command in all modules, or a subset of the modules in a pride")
class DoInPrideCommand extends AbstractPrideCommand {
	private static final Logger log = LoggerFactory.getLogger(DoInPrideCommand)

	@Option(name = ["-I", "--include"],
			title = "repo",
			description = "Execute the command on repo (can be specified multiple times)")
	private List<File> inlcudeRepos

	@Option(name = "--exclude",
			title = "repo",
			description = "Do not execute command on repo (can be specified multiple times)")
	private List<File> excludeRepos

	@Arguments(required = true, description = "The command to execute")
	private List<String> commandLine

	@Override
	void run() {
		Pride pride = new Pride(prideDirectory)
		def modules = (inlcudeRepos ? inlcudeRepos : pride.modules).sort { it.name }.findAll { includeRepo ->
			return null == excludeRepos.find { excludeRepo ->
				includeRepo.absoluteFile.equals(excludeRepo.absoluteFile)
			}
		}

		modules.each { moduleDirectory ->
			log.info "\n${moduleDirectory} \$ ${commandLine.join(" ")}"
			executeIn(moduleDirectory, commandLine)
		}
	}
}
