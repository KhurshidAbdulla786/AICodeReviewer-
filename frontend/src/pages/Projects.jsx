import React from 'react';
import Layout from '../components/Layout';
import { FolderGit2, Plus, Code, ArrowRight } from 'lucide-react';
import { Link } from 'react-router-dom';

export default function Projects() {
  return (
    <Layout>
      <div className="space-y-6 max-w-6xl mx-auto">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-2xl font-bold text-white">Projects</h1>
            <p className="text-sm text-gray-400">Manage your repositories and code review projects</p>
          </div>
          <Link
            to="/editor"
            className="bg-primary-600 hover:bg-primary-500 text-white px-4 py-2.5 rounded-xl text-sm font-medium transition-all flex items-center gap-2 shadow-lg shadow-primary-600/30"
          >
            <Plus className="w-4 h-4" /> New Project Review
          </Link>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div className="bg-gray-900 border border-gray-800 rounded-2xl p-6 hover:border-primary-500/40 transition-all flex flex-col justify-between">
            <div>
              <div className="w-10 h-10 rounded-xl bg-primary-500/10 text-primary-400 flex items-center justify-center mb-4 border border-primary-500/20">
                <FolderGit2 className="w-5 h-5" />
              </div>
              <h3 className="text-lg font-semibold text-white mb-1">Sample Spring Boot Project</h3>
              <p className="text-xs text-gray-400 mb-4">Java • Updated 2 hours ago</p>
              <p className="text-sm text-gray-300 mb-6">
                Comprehensive AI review analyzing security vulnerabilities, controller architecture, and service layer patterns.
              </p>
            </div>
            <Link
              to="/editor"
              className="inline-flex items-center gap-2 text-primary-400 hover:text-primary-300 text-sm font-medium"
            >
              Open Editor <ArrowRight className="w-4 h-4" />
            </Link>
          </div>
        </div>
      </div>
    </Layout>
  );
}
